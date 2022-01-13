package com.ertugrul.spring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//Kaydedilmeye çalışılan yanlış borç türü hatası
@ResponseStatus(HttpStatus.NOT_FOUND)
public class DebtTypeException extends RuntimeException {

    public DebtTypeException(String message) {
        super(message);
    }
}
