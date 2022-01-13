package com.ertugrul.spring.controller;


import com.ertugrul.spring.dto.DebtDto;
import com.ertugrul.spring.service.DebtService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/debts")
@CrossOrigin
@RequiredArgsConstructor
public class DebtController {
    private final DebtService debtService;

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) {

        DebtDto debtDto = debtService.findById(id);

        return ResponseEntity.ok(debtDto);
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody DebtDto debtDto) {
        return ResponseEntity.ok(debtService.save(debtDto));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        debtService.delete(id);
    }

    @GetMapping("")
    public ResponseEntity<Object> getAllDebts() {
        List<DebtDto> all = debtService.findAll();
        return ResponseEntity.ok(all);
    }

    /*http://localhost:8080/api/v1/debts/date?start=2020-08-11&end=2022-01-01*/
    @GetMapping("/date")
    public ResponseEntity<Object> getAllDebtsByDateRange(@RequestParam("start") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                                         @RequestParam("end") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        List<DebtDto> all = debtService.listDebtsByDateRange(startDate, endDate);
        return ResponseEntity.ok(all);
    }

    /*http://localhost:8080/api/v1/debts/users/1*/
    @GetMapping("/users/{userId}")
    public ResponseEntity<Object> getAllDebtsByUserId(@PathVariable Long userId) {
        List<DebtDto> debtDtoList = debtService.listAllUserDebtByUserId(userId);
        return ResponseEntity.ok(debtDtoList);
    }


}