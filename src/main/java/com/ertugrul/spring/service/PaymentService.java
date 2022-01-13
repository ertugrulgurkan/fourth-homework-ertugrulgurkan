package com.ertugrul.spring.service;


import com.ertugrul.spring.converter.PaymentMapper;
import com.ertugrul.spring.dto.PaymentDto;
import com.ertugrul.spring.entity.Debt;
import com.ertugrul.spring.entity.Payment;
import com.ertugrul.spring.entity.User;
import com.ertugrul.spring.exception.DebtNotFoundException;
import com.ertugrul.spring.exception.PaymentAndDebtAmountNotEqualException;
import com.ertugrul.spring.exception.UserNotFoundException;
import com.ertugrul.spring.service.entityservice.DebtEntityService;
import com.ertugrul.spring.service.entityservice.PaymentEntityService;
import com.ertugrul.spring.service.entityservice.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final UserEntityService userEntityService;
    private final PaymentEntityService paymentEntityService;
    private final DebtEntityService debtEntityService;


    @Transactional
    public PaymentDto savePayment(PaymentDto paymentDto){
        Optional<User> user = userEntityService.findById(paymentDto.getUserId());
        if(user.isEmpty())
            throw new UserNotFoundException("User not found.");
        Optional<Debt> debt = debtEntityService.findById(paymentDto.getDebtId());
        if(debt.isEmpty())
            throw new DebtNotFoundException("Debt not found.");
        PaymentDto savedPaymentDto = null;

        Payment payment = PaymentMapper.INSTANCE.convertPaymentDtoToPayment(paymentDto);

        if(!debt.get().getTotalAmount().equals(paymentDto.getDebtAmount()))
            throw new PaymentAndDebtAmountNotEqualException("Payment and Debt amounts must be equal.");

        //todo  debt update edilecek - gecikme zammı varsa hesaplanacak payment a eklenecek - gecikme zammı varsa debt tablosuna kayıt atılacak  - payment kaydedilecek.


        return savedPaymentDto;
    }
}
