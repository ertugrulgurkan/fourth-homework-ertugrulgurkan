package com.ertugrul.spring.service.entityservice;

import com.ertugrul.spring.entity.Payment;
import com.ertugrul.spring.repository.PaymentRepository;

public class PaymentEntityService extends BaseEntityService<Payment, PaymentRepository> {

    public PaymentEntityService(PaymentRepository paymentRepository) {
        super(paymentRepository);
    }

}
