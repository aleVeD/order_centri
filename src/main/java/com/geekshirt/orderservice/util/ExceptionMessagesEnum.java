package com.geekshirt.orderservice.util;

public enum ExceptionMessagesEnum {
    INCORRECT_REQUEST_EMPTY_ITEMS_ORDER("Empty items are not allowed in the order"),
    ACCOUNT_NOT_FOUND("Account not found");
    ExceptionMessagesEnum(String message){
        value = message;
    }

    private final String value ;

    public String getValue(){
        return value;
    }
}
