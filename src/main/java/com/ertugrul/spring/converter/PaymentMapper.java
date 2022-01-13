package com.ertugrul.spring.converter;

import com.ertugrul.spring.dto.PaymentDto;
import com.ertugrul.spring.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PaymentMapper {
    PaymentMapper INSTANCE = Mappers.getMapper(PaymentMapper.class);

    PaymentDto convertPaymentDtoToPayment(Payment payment);

    Payment convertPaymentDtoToPayment(PaymentDto paymentDto);

    List<PaymentDto> convertAllPaymentToPaymentDto(List<Payment> paymentList);

    List<Payment> convertAllPaymentDtoToPayment(List<PaymentDto> paymentDtoList);
}
