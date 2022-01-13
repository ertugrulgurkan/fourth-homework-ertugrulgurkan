package com.ertugrul.spring.converter;

import com.ertugrul.spring.dto.DebtDto;
import com.ertugrul.spring.entity.Debt;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DebtMapper {
    DebtMapper INSTANCE = Mappers.getMapper(DebtMapper.class);

    DebtDto convertDebtDtoToDebt(Debt debt);

    Debt convertDebtDtoToDebt(DebtDto debtDto);

    List<DebtDto> convertAllDebtToDebtDto(List<Debt> debtList);

    List<Debt> convertAllDebtDtoToDebt(List<DebtDto> debtDtoList);
}
