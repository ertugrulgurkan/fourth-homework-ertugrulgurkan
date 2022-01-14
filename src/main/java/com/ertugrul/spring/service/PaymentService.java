package com.ertugrul.spring.service;


import com.ertugrul.spring.converter.DebtMapper;
import com.ertugrul.spring.converter.PaymentMapper;
import com.ertugrul.spring.dto.DebtDto;
import com.ertugrul.spring.dto.PaymentDto;
import com.ertugrul.spring.entity.Debt;
import com.ertugrul.spring.entity.Payment;
import com.ertugrul.spring.entity.User;
import com.ertugrul.spring.enums.DebtType;
import com.ertugrul.spring.exception.DebtNotFoundException;
import com.ertugrul.spring.exception.PaymentAndDebtAmountNotEqualException;
import com.ertugrul.spring.exception.PaymentNotFoundException;
import com.ertugrul.spring.exception.UserNotFoundException;
import com.ertugrul.spring.service.entityservice.DebtEntityService;
import com.ertugrul.spring.service.entityservice.PaymentEntityService;
import com.ertugrul.spring.service.entityservice.UserEntityService;
import com.ertugrul.spring.util.Constant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final UserEntityService userEntityService;
    private final PaymentEntityService paymentEntityService;
    private final DebtEntityService debtEntityService;

    public List<PaymentDto> findAll() {

        List<Payment> paymentList = paymentEntityService.findAll();

        return PaymentMapper.INSTANCE.convertAllPaymentToPaymentDto(paymentList);
    }

    public PaymentDto findById(Long id) {

        Optional<Payment> optionalPayment = paymentEntityService.findById(id);

        Payment payment;
        if (optionalPayment.isPresent()) {
            payment = optionalPayment.get();
        } else {
            throw new PaymentNotFoundException("Payment not found!");
        }

        return PaymentMapper.INSTANCE.convertPaymentDtoToPayment(payment);
    }

    @Transactional
    public void delete(Long id) {

        Payment payment = findPaymentById(id);

        paymentEntityService.delete(payment);
    }

    private Payment findPaymentById(Long id) {
        Optional<Payment> optionalPayment = paymentEntityService.findById(id);

        Payment payment;
        if (optionalPayment.isPresent()) {
            payment = optionalPayment.get();
        } else {
            throw new PaymentNotFoundException("Payment not found!");
        }
        return payment;
    }

    //a. Tahsilat yapan operasyon
    @Transactional
    public PaymentDto save(PaymentDto paymentDto) {
        Optional<User> userOptional = userEntityService.findById(paymentDto.getUserId());
        if (userOptional.isEmpty())
            throw new UserNotFoundException("User not found.");
        Optional<Debt> debtOptional = debtEntityService.findById(paymentDto.getDebtId());
        if (debtOptional.isEmpty())
            throw new DebtNotFoundException("Debt not found.");
        PaymentDto savedPaymentDto = null;
        Debt debt = debtOptional.get();
        User user = userOptional.get();

        Payment payment = PaymentMapper.INSTANCE.convertPaymentDtoToPayment(paymentDto);

        if (!debt.getTotalAmount().equals(paymentDto.getDebtAmount()))
            throw new PaymentAndDebtAmountNotEqualException("Payment and Debt amounts must be equal.");

        double lateFeeDebt = 0;

        if (debt.getExpiryDate().before(paymentDto.getPaymentDate())) {
            lateFeeDebt = calculateLateFeeDebt(debt);
            Debt lateFeeD = buildLateFeeDebt(user, lateFeeDebt);
            debtEntityService.save(lateFeeD);
        }
        updateCurrentDebt(debt);

        payment.setTotalDebtAmount(lateFeeDebt + debt.getTotalAmount());
        Payment save = paymentEntityService.save(payment);
        savedPaymentDto = PaymentMapper.INSTANCE.convertPaymentDtoToPayment(save);
        return savedPaymentDto;
    }

    //b. Belirtilen tarihler arasında yapılan tahsilatlar listelenebilmelidir
    public List<PaymentDto> listDebtsByDateRange(Date startDate, Date endDate) {
        Optional<List<Payment>> paymentListOptional = paymentEntityService.findAllPaymentByPaymentDateBetween(startDate, endDate);

        if (paymentListOptional.isPresent())
            return PaymentMapper.INSTANCE.convertAllPaymentToPaymentDto(paymentListOptional.get());
        else
            throw new PaymentNotFoundException("No Payment has been found.");
    }

    //c. Kullanıcının tüm tahsilatları listelenebilmelidir.
    public List<PaymentDto> listAllUserPaymentByUserId(Long userId) {
        List<PaymentDto> paymentList;
        Optional<User> user = userEntityService.findById(userId);
        if (user.isPresent()) {
            Optional<List<Payment>> allPaymentByUserId = paymentEntityService.findAllPaymentByUserId(userId);
            if (allPaymentByUserId.isPresent())
                paymentList = PaymentMapper.INSTANCE.convertAllPaymentToPaymentDto(allPaymentByUserId.get());
            else
                throw new PaymentNotFoundException("Payment not found");
        } else
            throw new UserNotFoundException("User Id not found");

        return paymentList;
    }

    private Debt buildLateFeeDebt(User user, double lateFeeDebt) {
        return Debt.builder()
                .expiryDate(new Date())
                .totalAmount(lateFeeDebt)
                .remainingAmount(0.0)
                .userId(user.getId())
                .type(DebtType.LATE_FEE)
                .build();
    }

    private void updateCurrentDebt(Debt debt) {
        debt.setRemainingAmount(0.0);
        debtEntityService.save(debt);
    }

    private double calculateLateFeeDebt(Debt debt) {
        long months = Constant.monthsBetween(new Date(),debt.getExpiryDate());
        return Math.round(debt.getTotalAmount() * months * Constant.getLateFeeRate(debt.getExpiryDate()) / 100);
    }
}
