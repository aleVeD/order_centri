package com.geekshirt.orderservice.service;

import com.geekshirt.orderservice.client.CustomerServiceClient;
import com.geekshirt.orderservice.dto.AccountDto;
import com.geekshirt.orderservice.dto.OrderRequest;
import com.geekshirt.orderservice.dto.OrderResponse;
import com.geekshirt.orderservice.entities.Order;
import com.geekshirt.orderservice.util.OrderValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class OrderService {

    @Autowired
    private CustomerServiceClient client;

    public Order createOrder(OrderRequest orderRequest){
        OrderValidator.validateOrder(orderRequest);
        AccountDto account = client.findAccountById(orderRequest.getAccountId());
      /*  AccountDto dummyAccount = client.createDummyAccount();
        dummyAccount = client.createAccount(dummyAccount);
        dummyAccount.getAddress().setZipCode("386453");
        client.updateAccount(dummyAccount);

        AccountDto accountD = client.findAccountById(orderRequest.getAccountId());
        log.info(accountD.toString());

        client.deleteAccount(dummyAccount);
*/
        Order response1 = new Order();
        response1.setAccountId(orderRequest.getAccountId());
        response1.setOrderId("899997");
        response1.setStatus("created");
        response1.setTotalAmount(126.0);
        response1.setTotalTax(19.0);
        response1.setTransactionDate(new Date());
        return response1;
    }

    public List<Order> findAllOrders(){
        List<Order> orderList = new ArrayList<>();
        Order response = new Order();
        response.setOrderId("3432");
        response.setAccountId("new");
        response.setStatus("pending");
        response.setTotalAmount(100.0);
        response.setTotalTax(10.0);
        response.setTransactionDate(new Date());
        orderList.add(response);

        Order response1 = new Order();
        response1.setOrderId("5454");
        response1.setAccountId("jfdgdfg");
        response1.setStatus("pending");
        response1.setTotalAmount(136.0);
        response1.setTotalTax(12.0);
        response1.setTransactionDate(new Date());
        orderList.add(response1);
        return orderList;
    }

    public Order findOrderById(String orderId){


        Order response1 = new Order();
        response1.setOrderId(orderId);
        response1.setAccountId("ju");
        response1.setStatus("pending");
        response1.setTotalAmount(136.0);
        response1.setTotalTax(12.0);
        response1.setTransactionDate(new Date());

        return response1;
    }

}