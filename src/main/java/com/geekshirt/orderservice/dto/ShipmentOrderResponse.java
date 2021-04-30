package com.geekshirt.orderservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShipmentOrderResponse {
    @JsonProperty("orderId")
    private Long orderId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("receipEmail")
    private String receipEmail;

    @JsonProperty("trackingId")
    private String trackingId;

    @JsonProperty("address")
    private AddressDto address;

    @JsonProperty("shippingStatus")
    private String shippingStatus;

    @JsonProperty("shippingDate")
    private Date shippingDate;

    @JsonProperty("deliveryDate")
    private Date deliveryDate;
}
