package com.ertugrul.spring.repository;

import com.ertugrul.spring.entity.Payment;
import com.ertugrul.spring.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<List<Payment>> findAllPaymentByUserId(Long userId);

    Optional<List<Payment>> findAllPaymentByPaymentDateBetween(Date startDate, Date endDate);

}
