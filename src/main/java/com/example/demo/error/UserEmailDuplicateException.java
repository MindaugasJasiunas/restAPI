package com.example.demo.error;

public class UserEmailDuplicateException extends RuntimeException{
    public UserEmailDuplicateException(String msg){
        super(msg);
    }
}
