package com.ertugrul.spring.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class PaymentDto implements Serializable {
    private final Long id;
    private final Long userId;
    private final Long debtId;
    private final Double debtAmount;
    private final Double totalDebtAmount;
    private final Date paymentDate;
}
