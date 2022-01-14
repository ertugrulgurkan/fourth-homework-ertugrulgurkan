package com.ertugrul.spring.controller;


import com.ertugrul.spring.dto.*;
import com.ertugrul.spring.service.DebtService;
import com.ertugrul.spring.service.PaymentService;
import com.ertugrul.spring.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final DebtService debtService;
    private final PaymentService paymentService;

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) {

        UserDto userDto = userService.findById(id);

        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<Object> getByUsername(@PathVariable String username) {

        UserDto userDto = userService.findByUsername(username);

        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/")
    public ResponseEntity<Object> getAllUsers() {
        List<UserDto> all = userService.findAll();
        return ResponseEntity.ok(all);
    }


    @PostMapping
    public ResponseEntity<Object> create(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.save(userDto));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

    /*http://localhost:8080/api/v1/users/1/debts*/
    @GetMapping("/{id}/debts")
    public ResponseEntity<Object> getAllDebtsByUserId(@PathVariable Long id) {
        List<DebtDto> debtDtoList = debtService.listAllUserDebtByUserId(id);
        return ResponseEntity.ok(debtDtoList);
    }

    /*http://localhost:8080/api/v1/users/1/overdue-debts*/
    @GetMapping("/{id}/overdue-debts")
    public ResponseEntity<Object> getAllDOverdueDebtsByUserId(@PathVariable Long id) {
        List<DebtDto> debtDtoList = debtService.listAllUserOverdueDebtByUserId(id);
        return ResponseEntity.ok(debtDtoList);
    }

    /*http://localhost:8080/api/v1/users/1/total-overdue-debt*/
    @GetMapping("/{id}/total-overdue-debt")
    public ResponseEntity<Object> getTotalDOverdueDebtByUserId(@PathVariable Long id) {
        OverdueTotalDebtDto totalOverdueDebtByUserId = debtService.findTotalOverdueDebtByUserId(id);
        return ResponseEntity.ok(totalOverdueDebtByUserId);
    }


    /*http://localhost:8080/api/v1/users/1/total-late-fee*/
    @GetMapping("/{id}/total-late-fee")
    public ResponseEntity<Object> getTotalLateFeeAmountByUserId(@PathVariable Long id) {
        OverdueDebtDto allOverdueDebtLateFeeAmountByUserId = debtService.findAllOverdueDebtLateFeeAmountByUserId(id);
        return ResponseEntity.ok(allOverdueDebtLateFeeAmountByUserId);
    }


    /*http://localhost:8080/api/v1/users/1/late-fees*/
    @GetMapping("/{id}/late-fees")
    public ResponseEntity<Object> getAllLateFeeDebtsByUserId(@PathVariable Long id) {
        List<DebtDto> allLateFeeDebtByUserId = debtService.findAllLateFeeDebtByUserId(id);
        return ResponseEntity.ok(allLateFeeDebtByUserId);
    }


    /*http://localhost:8080/api/v1/users/1/late-fees*/
    @GetMapping("/{id}/payments")
    public ResponseEntity<Object> getAllPaymentsByUserId(@PathVariable Long id) {
        List<PaymentDto> paymentList = paymentService.listAllUserPaymentByUserId(id);
        return ResponseEntity.ok(paymentList);
    }

}