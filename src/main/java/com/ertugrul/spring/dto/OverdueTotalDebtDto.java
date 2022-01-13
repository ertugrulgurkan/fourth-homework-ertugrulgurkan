package com.ertugrul.spring.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class OverdueTotalDebtDto implements Serializable {
    private final Long userId;
    private final Double totalDebtAmount;
}
