package com.ertugrul.spring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

//custom exceptionların tutulduğu sınıf
@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest webRequest) {
        return getErrorResponse(ex, webRequest, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex, WebRequest webRequest) {
        return getErrorResponse(ex, webRequest, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handlePaymentNotFoundException(PaymentNotFoundException ex, WebRequest webRequest) {
        return getErrorResponse(ex, webRequest, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleDebtNotFoundException(DebtNotFoundException ex, WebRequest webRequest) {
        return getErrorResponse(ex, webRequest, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleDebtTypeException(DebtTypeException ex, WebRequest webRequest) {
        return getErrorResponse(ex, webRequest, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Object> getErrorResponse(Exception ex, WebRequest webRequest, HttpStatus notFound) {
        Date errorDate = new Date();
        String description = webRequest.getDescription(false);
        ExceptionResponse exceptionResponse = new ExceptionResponse(errorDate, ex.getMessage(), description);
        return new ResponseEntity<>(exceptionResponse, notFound);
    }

}
