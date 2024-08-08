package com.frutz.pft.controller;

import com.frutz.pft.dto.ExpenseDTO;
import com.frutz.pft.entity.Expense;
import com.frutz.pft.services.expense.ExpenseService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public ResponseEntity<?> getExpenseById(@PathVariable long id) {
        try {
            return ResponseEntity.ok(expenseService.getExpenseById(id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> putExpense(@PathVariable long id, @RequestBody ExpenseDTO eDTO) {
        try {
            return ResponseEntity.ok(expenseService.updateExpense(id, eDTO));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteExpense(@PathVariable long id) {
        try {
            expenseService.deleteExpense(id);
            return ResponseEntity.ok(null);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
