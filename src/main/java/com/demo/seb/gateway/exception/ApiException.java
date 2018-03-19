package com.demo.seb.gateway.exception;

public class ApiException extends RuntimeException {

    private static final long serialVersionUID = 2607293592737464935L;

    public ApiException(String message) {
        super(message);
    }
}