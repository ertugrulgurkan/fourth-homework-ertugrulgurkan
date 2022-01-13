package com.ertugrul.spring.service.entityservice;

import com.ertugrul.spring.entity.Debt;
import com.ertugrul.spring.enums.DebtType;
import com.ertugrul.spring.repository.DebtRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DebtEntityService extends BaseEntityService<Debt, DebtRepository> {
    public DebtEntityService(DebtRepository repository) {
        super(repository);
    }

    public Optional<List<Debt>> findAllDebtByExpiryDateBetween(Date startDate, Date endDate) {
        return getRepository().findAllDebtByExpiryDateBetween(startDate, endDate);
    }

    public Optional<List<Debt>> findAllDebtByUserId(Long userId) {
        return getRepository().findAllDebtByUserIdAndRemainingAmountIsGreaterThan(userId, (double) 0);
    }

    public Optional<List<Debt>> findAllOverdueDebtByUserId(Long userId) {
        return getRepository().findAllDebtByUserIdAndRemainingAmountIsGreaterThanAndExpiryDateIsLessThan(userId, (double) 0, new Date(System.currentTimeMillis()));
    }

    public Optional<List<Debt>> findAllDebtByUserIdAndType(Long userId) {
        return getRepository().findAllDebtByUserIdAndType(userId, DebtType.LATE_FEE);
    }

}
