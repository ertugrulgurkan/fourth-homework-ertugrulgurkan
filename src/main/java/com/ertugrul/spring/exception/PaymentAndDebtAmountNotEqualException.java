package com.ertugrul.spring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//Tahsilat ve ödeme tutarı birbirine eşit olmadığı zaman fırlatılacak hata kodu.
@ResponseStatus(HttpStatus.NOT_FOUND)
public class PaymentAndDebtAmountNotEqualException extends RuntimeException {

    public PaymentAndDebtAmountNotEqualException(String message) {
        super(message);
    }
}
