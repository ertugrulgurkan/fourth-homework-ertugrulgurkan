package com.ertugrul.spring.service;

import com.ertugrul.spring.entity.Debt;
import com.ertugrul.spring.entity.Payment;
import com.ertugrul.spring.entity.User;
import com.ertugrul.spring.exception.PaymentNotFoundException;
import com.ertugrul.spring.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ValidationService {

    public User validateUser(Optional<User> user) {
        if (user.isPresent()) {
            return user.get();
        } else
            throw new UserNotFoundException("User not found!");
    }

    public Debt validateDebt(Optional<Debt> debt) {
        if (debt.isPresent()) {
            return debt.get();
        } else
            throw new PaymentNotFoundException("Debt not found");
    }

    public List<Debt> validateDebtList(Optional<List<Debt>> debtList) {
        if (debtList.isPresent()) {
            return debtList.get();
        } else
            throw new PaymentNotFoundException("No Debt has been found.");
    }

    public Payment validatePayment(Optional<Payment> payment) {
        if (payment.isPresent()) {
            return payment.get();
        } else
            throw new PaymentNotFoundException("Payment not found");
    }

    public List<Payment> validatePaymentList(Optional<List<Payment>> paymentList) {
        if (paymentList.isPresent()) {
            return paymentList.get();
        } else
            throw new PaymentNotFoundException("No Payment has been found.");
    }
}
