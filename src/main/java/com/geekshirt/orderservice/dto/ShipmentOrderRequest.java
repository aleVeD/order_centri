package com.geekshirt.orderservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShipmentOrderRequest implements Serializable {
    @JsonProperty("orderId")
    private String orderId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("receipEmail")
    private String receipEmail;

    @JsonProperty("shipmentAddress")
    private AddressDto shipmentAddress;
}
