package com.example.demo.error;

public class UserCreationException  extends RuntimeException {
    public UserCreationException(String msg) {
        super(msg);
    }
}
