package com.ertugrul.spring.service.entityservice;

import com.ertugrul.spring.entity.Payment;
import com.ertugrul.spring.entity.Payment;
import com.ertugrul.spring.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentEntityService extends BaseEntityService<Payment, PaymentRepository> {

    public PaymentEntityService(PaymentRepository paymentRepository) {
        super(paymentRepository);
    }
    public Optional<List<Payment>> findAllPaymentByUserId(Long userId) {
        return getRepository().findAllPaymentByUserId(userId);
    }

    public Optional<List<Payment>> findAllPaymentByPaymentDateBetween(Date startDate, Date endDate) {
        return getRepository().findAllPaymentByPaymentDateBetween(startDate, endDate);
    }


}
