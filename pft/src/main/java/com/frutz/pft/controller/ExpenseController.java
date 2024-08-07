package com.frutz.pft.controller;

import com.frutz.pft.dto.ExpenseDTO;
import com.frutz.pft.entity.Expense;
import com.frutz.pft.services.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expense")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ExpenseController {
    private final ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<?> postExpense(@RequestBody ExpenseDTO eDTO) {
        Expense createdExpense = expenseService.postExpense(eDTO);

        if(createdExpense != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(createdExpense);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllExpenses() {
        return ResponseEntity.ok(expenseService.getExpenses());
    }

}
