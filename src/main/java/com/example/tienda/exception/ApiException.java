package com.example.tienda.exception;

public class ApiException extends RuntimeException {
    public ApiException(String message) { super(message); }
}