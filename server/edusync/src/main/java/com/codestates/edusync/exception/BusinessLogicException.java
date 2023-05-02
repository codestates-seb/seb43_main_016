package com.codestates.edusync.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BusinessLogicException extends RuntimeException{

    private ExceptionCode exceptionCode;
    private String message;

    public BusinessLogicException(ExceptionCode exceptionCode){
        this.exceptionCode = exceptionCode;
        this.message = null;
    }


    @Override
    public String getMessage(){
        if(message == null){
            return exceptionCode.getMessage();
        }

        return String.format("%s. %s,", exceptionCode.getMessage(), message);
    }
}
