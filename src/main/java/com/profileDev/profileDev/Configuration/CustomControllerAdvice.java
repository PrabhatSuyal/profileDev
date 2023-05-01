package com.profileDev.profileDev.Configuration;

import com.profileDev.profileDev.Exceptions.ControllerException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)         //IllegalArgumentException.class
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException e){
        return new ResponseEntity<Object>("#### CustomControllerAdvice >> handleIllegalArgumentException >> "+e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ControllerException.class)         //calling custom exception using Global exception
    public ResponseEntity<?> handleIllegalArgumentException(ControllerException e){
        return new ResponseEntity<Object>("#### CustomControllerAdvice >> handleIllegalArgumentException >> "+e.getErrorMessage()+" ROOT ERROR MSG : "+e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
