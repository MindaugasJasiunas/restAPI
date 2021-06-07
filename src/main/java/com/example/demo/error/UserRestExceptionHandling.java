package com.example.demo.error;

import com.example.demo.user.UserEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserRestExceptionHandling {

    @ExceptionHandler
    public ResponseEntity< UserErrorResponse> handleException(UserNotFoundException e) {
        UserErrorResponse error = new UserErrorResponse();
        error.setStatus(HttpStatus.BAD_REQUEST.value()); //400
        error.setMessage(e.getMessage());
        error.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST); //Jackson will convert to JSON for return
    }

    @ExceptionHandler
    public ResponseEntity< UserErrorResponse> handleException(UserEmailDuplicateException e) {
        UserErrorResponse error = new UserErrorResponse();
        error.setStatus(HttpStatus.BAD_REQUEST.value()); //400
        error.setMessage(e.getMessage());
        error.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST); //Jackson will convert to JSON for return
    }
}
