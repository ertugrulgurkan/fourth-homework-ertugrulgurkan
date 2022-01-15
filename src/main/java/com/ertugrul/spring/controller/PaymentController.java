package com.ertugrul.spring.controller;

import com.ertugrul.spring.dto.PaymentDto;
import com.ertugrul.spring.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/payments")
@CrossOrigin
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    //GET http://localhost:8080/api/v1/payments/1
    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) {

        PaymentDto paymentDto = paymentService.findById(id);

        return ResponseEntity.ok(paymentDto);
    }

    // POST http://localhost:8080/api/v1/debts'
    //a. Tahsilat yapan operasyon
    @PostMapping
    public ResponseEntity<Object> create(@RequestBody PaymentDto paymentDto) {
        return ResponseEntity.ok(paymentService.save(paymentDto));
    }

    // DELETE http://localhost:8080/api/v1/payments/1
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        paymentService.delete(id);
    }

    // GET http://localhost:8080/api/v1/payments'
    @GetMapping("")
    public ResponseEntity<Object> getAllPayments() {
        List<PaymentDto> all = paymentService.findAll();
        return ResponseEntity.ok(all);
    }

    /*http://localhost:8080/api/v1/payments/date?start=2020-08-11&end=2022-01-01*/
    //b. Belirtilen tarihler arasında yapılan tahsilatlar listelenebilmelidir
    @GetMapping("/date")
    public ResponseEntity<Object> getAllPaymentsByDateRange(@RequestParam("start") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                                            @RequestParam("end") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        List<PaymentDto> all = paymentService.listDebtsByDateRange(startDate, endDate);
        return ResponseEntity.ok(all);
    }

}