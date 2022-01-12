package com.ertugrul.spring.service.entityservice;

import com.ertugrul.spring.entity.Debt;
import com.ertugrul.spring.repository.DebtRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class DebtEntityService extends BaseEntityService<Debt, DebtRepository> {
    public DebtEntityService(DebtRepository repository) {
        super(repository);
    }

    public Optional<List<Debt>> findAllDebtByExpiryDateBetween(Date startDate, Date endDate) {
        return getRepository().findAllDebtByExpiryDateBetween(startDate, endDate);
    }

    public Optional<List<Debt>> findAllDebtByUserId(Long userId) {
        return getRepository().findAllDebtByUserIdAndTotalAmountIsGreaterThan(userId, (double) 0);
    }

}
