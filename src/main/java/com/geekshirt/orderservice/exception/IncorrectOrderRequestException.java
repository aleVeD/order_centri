package com.geekshirt.orderservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Empty items not allowed")
public class IncorrectOrderRequestException extends RuntimeException{
}
