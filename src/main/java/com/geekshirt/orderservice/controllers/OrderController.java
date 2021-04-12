package com.geekshirt.orderservice.controllers;
import com.geekshirt.orderservice.dto.OrderRequest;
import com.geekshirt.orderservice.dto.OrderResponse;
import com.geekshirt.orderservice.entities.Order;
import com.geekshirt.orderservice.service.OrderService;
import com.geekshirt.orderservice.util.EntityDtoConverter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Api
@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private EntityDtoConverter converter;


    @ApiOperation(value = "Retrieve all existed orders", notes = "This operations return all store orders")
    @GetMapping("/order")
    public ResponseEntity<List<OrderResponse>> findAll(){
        List<Order> orderList = orderService.findAllOrders();

        return new ResponseEntity<>(converter.convertEntityToDto(orderList), HttpStatus.OK);
    }
    @ApiOperation(value = "Retrieve an order based on Id", notes = "This operation return an order using its id")
    @GetMapping("/orders/{id}")
    public ResponseEntity<OrderResponse> findByOrderId(@PathVariable("id") String orderId){
        Order order = orderService.findOrderByOrderId(orderId);
        return new ResponseEntity<>( converter.convertEntityToDto(order), HttpStatus.OK);
    }

    @ApiOperation(value = "Retrieve an order based on Id", notes = "This operation return an order using its id")
    @GetMapping("/order/{id}")
    public ResponseEntity<OrderResponse> findById(@PathVariable("id") long orderId){
        Order order = orderService.findOrderById(orderId);
        return new ResponseEntity<>( converter.convertEntityToDto(order), HttpStatus.OK);
    }

    @ApiOperation(value = "Retrieve an order based on Id", notes = "This operation return an orders by accountId")
    @GetMapping("/order/generated/{accountId}")
    public ResponseEntity<List<OrderResponse>> findOrdersByAccountId(@PathVariable("accountId") String accountId){
        List<Order> orders = orderService.findOrdersByAccountId(accountId);
        return new ResponseEntity<>( converter.convertEntityToDto(orders), HttpStatus.OK);
    }

    @ApiOperation(value = "Create an order", notes = "This operation creates a new order")
    @PostMapping("/order/create")
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest payload){
        Order order = orderService.createOrder(payload);
        return new ResponseEntity<>(converter.convertEntityToDto(order), HttpStatus.CREATED);
    }
}
