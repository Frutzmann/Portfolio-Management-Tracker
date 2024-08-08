package com.frutz.pft.controller;

import com.frutz.pft.dto.IncomeDTO;
import com.frutz.pft.entity.Income;
import com.frutz.pft.services.income.IncomeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

@RestController
@RequestMapping("/api/income")
@RequiredArgsConstructor
public class IncomeController {
    private final IncomeService incomeService;

    @PostMapping
    public ResponseEntity<?> postExpense(@RequestBody IncomeDTO iDTO) {
        Income cIncome = incomeService.postIncome(iDTO);

        if (cIncome == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(cIncome);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllIncomes() {
        return ResponseEntity.ok(incomeService.getAllIncomes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getIncomeById(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(incomeService.getIncomeById(id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> putExpense(@PathVariable Integer id, @RequestBody IncomeDTO iDTO) {
        try {
            return ResponseEntity.ok(incomeService.updateIncome(id, iDTO));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteIncome(@PathVariable Integer id) {
        try {
            incomeService.deleteIncome(id);
            return ResponseEntity.ok(null);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
