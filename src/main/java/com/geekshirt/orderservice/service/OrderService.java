package com.geekshirt.orderservice.service;

import com.geekshirt.orderservice.client.CustomerServiceClient;
import com.geekshirt.orderservice.client.InventoryServiceClient;
import com.geekshirt.orderservice.dto.AccountDto;
import com.geekshirt.orderservice.dto.Confirmation;
import com.geekshirt.orderservice.dto.OrderRequest;
import com.geekshirt.orderservice.entities.Order;
import com.geekshirt.orderservice.entities.OrderDetail;
import com.geekshirt.orderservice.exception.AccountNotFoundException;
import com.geekshirt.orderservice.exception.OrderNotFoundException;
import com.geekshirt.orderservice.exception.PaymentNotAcceptedException;
import com.geekshirt.orderservice.repositories.OrderRepository;
import com.geekshirt.orderservice.util.Constants;
import com.geekshirt.orderservice.util.ExceptionMessagesEnum;
import com.geekshirt.orderservice.util.OrderPaymentStatus;
import com.geekshirt.orderservice.util.OrderStatus;
import com.geekshirt.orderservice.util.OrderValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerServiceClient client;

    @Autowired
    private PaymentProcessorService paymentService;

    @Autowired
    private InventoryServiceClient inventoryClient;



   // @Autowired
   // private JpaOrderDAO jpaOrderDAO;

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Order createOrder(OrderRequest orderRequest) throws PaymentNotAcceptedException {
        OrderValidator.validateOrder(orderRequest);
        log.info("Validated");
        AccountDto account = client.findAccountById(orderRequest.getAccountId())
                .orElseThrow(()-> new AccountNotFoundException(ExceptionMessagesEnum.ACCOUNT_NOT_FOUND.getValue()));
        Order newOrder = initOrder(orderRequest);
        Confirmation confirmation = paymentService.processPayment(newOrder, account);

        log.info("Payment Confirmation: {}", confirmation);

        String paymentStatus = confirmation.getTransactionStatus();
        newOrder.setPaymentStatus(OrderPaymentStatus.valueOf(paymentStatus));

        if (paymentStatus.equals(OrderPaymentStatus.DENIED.name())) {
            newOrder.setStatus(OrderStatus.NA);
            orderRepository.save(newOrder);
            throw new PaymentNotAcceptedException("The Payment added to your account was not accepted, please verify.");
        }

        log.info("Updating Inventory: {}", orderRequest.getItems());
        inventoryClient.updateInventory(orderRequest.getItems());

        log.info("Sending Request to Shipping Service.");
        //shipmentMessageProducer.send(newOrder.getOrderId(), account);
        return orderRepository.save(newOrder);
       // return jpaOrderDAO.save(newOrder);
    }

    private Order initOrder(OrderRequest orderRequest){
        Order orderObj = new Order();
        orderObj.setOrderId(UUID.randomUUID().toString());
        log.info("set uuid");
        orderObj.setAccountId(orderRequest.getAccountId());
        log.info("set account id");
        orderObj.setStatus(OrderStatus.PENDING);
        List<OrderDetail> orderDetails = orderRequest.getItems().stream()
                .map(item-> OrderDetail.builder().price(item.getPrice())
                .quantity(item.getQuatity())
                .upc(item.getUpc())
                .tax(item.getQuatity() * item.getPrice()* Constants.TAX_IMPORT)
                .order(orderObj)
                .build()).collect(Collectors.toList());
        orderObj.setDetails(orderDetails);
        orderObj.setTotalAmount(orderDetails.stream().mapToDouble(OrderDetail::getPrice).sum());
        orderObj.setTotalTax(orderObj.getTotalAmount() * Constants.TAX_IMPORT);
        orderObj.setTransactionDate(new Date());
        return orderObj;
    }

   /* public List<Order> findAllOrders(){
        return jpaOrderDAO.findAll();
    }
    */
    public List<Order> findAllOrders(){
        return orderRepository.findAll();
    }
/*
    public Order findOrderByOrderId(String orderId){
        return jpaOrderDAO.findByOrderId(orderId)
                .orElseThrow(()-> new OrderNotFoundException("Order Not Found"));
    }

 */
    public Order findOrderByOrderId(String orderId) {
        Optional<Order> order = Optional.ofNullable(orderRepository.findOrderByOrderId(orderId));
        return order.orElseThrow(()->new OrderNotFoundException("Order not found"));
    }
/*
    public Order findOrderById(Long orderId){
        return jpaOrderDAO.findById(orderId)
                .orElseThrow(()-> new OrderNotFoundException("Order Not Found"));
    }

 */
    public Order findOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(()->new OrderNotFoundException("Order not found"));
    }

    public List<Order> findOrdersByAccountId(String accountId){
        Optional<List<Order>> orders = Optional.ofNullable(orderRepository.findOrderByAccountId(accountId));
        return  orders.orElseThrow(()->new OrderNotFoundException("Order not found"));
    }

}