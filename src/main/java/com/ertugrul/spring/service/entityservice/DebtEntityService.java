package com.ertugrul.spring.service.entityservice;

import com.ertugrul.spring.entity.Debt;
import com.ertugrul.spring.repository.DebtRepository;

public class DebtEntityService extends BaseEntityService<Debt, DebtRepository> {
    public DebtEntityService(DebtRepository repository) {
        super(repository);
    }
}
