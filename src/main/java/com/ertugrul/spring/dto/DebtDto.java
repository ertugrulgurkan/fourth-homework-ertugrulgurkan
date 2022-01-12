package com.ertugrul.spring.dto;

import com.ertugrul.spring.entity.Debt;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class DebtDto implements Serializable {
    private final Long id;
    private final Long userId;
    private final Double totalAmount;
    private final Double remainingAmount;
    private final Date expiryDate;
    private final Debt.DebtType type;
}
