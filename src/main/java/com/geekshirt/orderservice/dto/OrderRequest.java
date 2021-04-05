package com.geekshirt.orderservice.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NonNull;

import java.util.List;

@Getter
@ApiModel(description = "Class representing an order to processed")
public class OrderRequest {
    @NonNull
    @ApiModelProperty(notes = "Account ID", example = "9896")
    private String accountId;
    @ApiModelProperty(notes = "Items included in the order", required = true)
    private List<LineItem> items;
}
