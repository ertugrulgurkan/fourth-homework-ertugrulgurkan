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

    //GET http://localhost:8080/api/v1/debts/1
    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) {

        DebtDto debtDto = debtService.findById(id);

        return ResponseEntity.ok(debtDto);
    }

    // POST http://localhost:8080/api/v1/debts'
    @PostMapping
    public ResponseEntity<Object> create(@RequestBody DebtDto debtDto) {
        return ResponseEntity.ok(debtService.save(debtDto));
    }


    //DELETE http://localhost:8080/api/v1/debts/1
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        debtService.delete(id);
    }

    // GET http://localhost:8080/api/v1/debts'
    @GetMapping("")
    public ResponseEntity<Object> getAllDebts() {
        List<DebtDto> all = debtService.findAll();
        return ResponseEntity.ok(all);
    }

    /*http://localhost:8080/api/v1/debts/date?start=2020-08-11&end=2022-01-01*/
    //d. Belirtilen tarihler arasında oluşturulan borçlar listelenebilmelidir.
    @GetMapping("/date")
    public ResponseEntity<Object> getAllDebtsByDateRange(@RequestParam("start") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                                         @RequestParam("end") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        List<DebtDto> all = debtService.listDebtsByDateRange(startDate, endDate);
        return ResponseEntity.ok(all);
    }

}