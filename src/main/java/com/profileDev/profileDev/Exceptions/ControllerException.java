package com.profileDev.profileDev.Exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
public class ControllerException extends RuntimeException{

    String errorCode;
    String errorMessage;

    public ControllerException() {
    }
}
