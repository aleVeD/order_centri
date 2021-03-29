package com.geekshirt.orderservice.controllers;
import com.geekshirt.orderservice.dto.OrderResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

import java.util.Date;
import java.util.List;
@Api
@RestController
public class OrderController {

    @ApiOperation(value = "Retrieve all existed orders", notes = "This operations return all store orders")
    @GetMapping("/order")
    public ResponseEntity<List<OrderResponse>> findAll(){
        List<OrderResponse> orderList = new ArrayList<>();
        OrderResponse response = new OrderResponse();
        response.setOrderId("3432");
        response.setAccountId("new");
        response.setStatus("pending");
        response.setTotalAmount(100.0);
        response.setTotalTax(10.0);
        response.setTransactionDate(new Date());
        orderList.add(response);

        OrderResponse response1 = new OrderResponse();
        response1.setOrderId("5454");
        response1.setAccountId("jfdgdfg");
        response1.setStatus("pending");
        response1.setTotalAmount(136.0);
        response1.setTotalTax(12.0);
        response1.setTransactionDate(new Date());
        orderList.add(response1);
        return new ResponseEntity<>(orderList, HttpStatus.OK);
    }
    @ApiOperation(value = "Retrieve an order based on Id", notes = "This operation return an order using its id")
    @GetMapping("/order/{id}")
    public ResponseEntity<OrderResponse> findById(@PathVariable("id") String orderId){

        OrderResponse response1 = new OrderResponse();
        response1.setOrderId(orderId);
        response1.setAccountId("ju");
        response1.setStatus("pending");
        response1.setTotalAmount(136.0);
        response1.setTotalTax(12.0);
        response1.setTransactionDate(new Date());
        return new ResponseEntity<>(response1, HttpStatus.OK);
    }
    @ApiOperation(value = "Create an order", notes = "This operation creates a new order")
    @PostMapping("order/create")
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest payload){
        OrderResponse response1 = new OrderResponse();
        response1.setAccountId(payload.getAccountId());
        System.out.println(payload.getAccountId());
        response1.setOrderId("899997");
        response1.setStatus("created");
        response1.setTotalAmount(126.0);
        response1.setTotalTax(19.0);
        response1.setTransactionDate(new Date());
        return new ResponseEntity<>(response1, HttpStatus.CREATED);
    }

}
