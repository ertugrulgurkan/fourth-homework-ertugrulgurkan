package com.ertugrul.spring.repository;

import com.ertugrul.spring.entity.Debt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface DebtRepository extends JpaRepository<Debt, Long> {

    Optional<List<Debt>> findAllDebtByExpiryDateBetween(Date startDate, Date endDate);

    Optional<List<Debt>> findAllDebtByUserIdAndRemainingAmountIsGreaterThan(Long userId, Double totalAmount);

    Optional<List<Debt>> findAllDebtByUserIdAndRemainingAmountIsGreaterThanAndExpiryDateIsLessThan(Long userId, Double remainingAmount,Date expiryDate);

}
