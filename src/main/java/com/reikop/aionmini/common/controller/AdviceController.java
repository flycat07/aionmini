package com.reikop.aionmini.common.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
public class AdviceController {

    @ResponseStatus(HttpStatus.GATEWAY_TIMEOUT)
    @ExceptionHandler({java.net.SocketTimeoutException.class})
    public HashMap<String, Object> timeoutHandler(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("type", "timeout");
        return result;
    }

}
