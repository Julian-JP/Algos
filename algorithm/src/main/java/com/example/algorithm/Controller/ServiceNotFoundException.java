package com.example.algorithm.Controller;

public class ServiceNotFoundException extends Throwable {
    public ServiceNotFoundException(String message) {
        super(message);
    }
}
