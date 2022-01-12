package com.ertugrul.spring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//Tanımlı kullanıcı borcu bulunmadığında gösterilecek özel hata tanımı
@ResponseStatus(HttpStatus.NOT_FOUND)
public class DebtNotFoundException extends RuntimeException {

    public DebtNotFoundException(String message) {
        super(message);
    }
}
