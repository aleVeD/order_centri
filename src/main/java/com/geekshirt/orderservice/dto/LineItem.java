package com.geekshirt.orderservice.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "Class that represent an item included in the order")
public class LineItem {
    @ApiModelProperty(notes = "UPC(Universal Product Code), length= 12 digits", example = "353250284701", required = true, position = 0)
    private String upc;
    @ApiModelProperty(notes = "quantity", example = "5", required = true, position = 1)
    private int quatity;
    @ApiModelProperty(notes = "Price", example = "10.00", required = true, position = 2)
    private double price;
}
