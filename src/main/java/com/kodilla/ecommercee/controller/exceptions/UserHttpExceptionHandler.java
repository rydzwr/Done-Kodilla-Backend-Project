package com.kodilla.ecommercee.controller.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class UserHttpExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException() {
        return new ResponseEntity<>("User with given id does not exist", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidKeyException.class)
    public ResponseEntity<Object> handleInvalidKeyException() {
        return new ResponseEntity<>("User's key is invalid", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BlockedUserException.class)
    public ResponseEntity<Object> handleBlockedUserException() {
        return new ResponseEntity<>("Account was blocked", HttpStatus.BAD_REQUEST);
    }
}
