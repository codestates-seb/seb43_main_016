package com.codestates.edusync.exception.advice;

import com.codestates.edusync.exception.BusinessLogicException;
import com.codestates.edusync.exception.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@Slf4j
@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValidException(
                               MethodArgumentNotValidException e) {

        return ErrorResponse.of(e.getBindingResult());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleConstraintViolationException(
                               ConstraintViolationException e) {

        return ErrorResponse.of(e.getConstraintViolations());
    }

    @ExceptionHandler
    public ErrorResponse handleHttpRequestMethodNotSupportedException(
                               HttpRequestMethodNotSupportedException e) {

        return ErrorResponse.of(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
    }

    @ExceptionHandler
    public ErrorResponse handleHttpMessageNotReadableException(
                               HttpMessageNotReadableException e) {

        return ErrorResponse.of(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler
    public ErrorResponse handleIllegalArgumentException(
                               IllegalArgumentException e) {

        return ErrorResponse.of(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler
    public ErrorResponse handleMissingServletRequestParameterException(
                               MissingServletRequestParameterException e) {

        return ErrorResponse.of(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity handleBusinessLogicException(BusinessLogicException e) {
        final ErrorResponse response = ErrorResponse.of(e.getExceptionCode());

        return new ResponseEntity<>(response, HttpStatus.valueOf(e.getExceptionCode()
                .getStatus()));
    }
}
