package com.geekshirt.orderservice.dto;

import com.geekshirt.orderservice.entities.Order;
import lombok.Data;

@Data
public class OrderDetailsResponse {

    private long id;
    private Integer quantity;
    private Double price;
    private Double tax;
    private String upc;
    private Order order;
}
