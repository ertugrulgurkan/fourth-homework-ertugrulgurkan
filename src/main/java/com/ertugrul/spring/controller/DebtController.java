package com.ertugrul.spring.controller;


import com.ertugrul.spring.dto.DebtDto;
import com.ertugrul.spring.service.DebtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}