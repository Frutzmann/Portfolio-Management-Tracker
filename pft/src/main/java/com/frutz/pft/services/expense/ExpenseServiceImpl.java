package com.frutz.pft.services.expense;

import com.frutz.pft.dto.ExpenseDTO;
import com.frutz.pft.entity.Expense;
import com.frutz.pft.repository.ExpenseRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;

    public Expense postExpense(ExpenseDTO expenseDTO) {
        return saveOrUpdateExpense(new Expense(), expenseDTO);
    }

    public List<Expense> getExpenses() {
        return expenseRepository.findAll().stream()
                .sorted(Comparator.comparing(Expense::getDate).reversed())
                .collect(Collectors.toList());

    }

    public Expense getExpenseById(long id) {
        Expense expense = expenseRepository.findById(id).orElse(null);

        if (expense == null) {
            throw new EntityNotFoundException("Expense with id " + id + " not found");
        }

        return expense;
    }

    public Expense updateExpense(long id, ExpenseDTO expenseDTO) {
        Expense expense = expenseRepository.findById(id).orElse(null);

        if (expense == null) {
            throw new EntityNotFoundException("Error - Expense not found or does not exist - " + id);
        }
        return saveOrUpdateExpense(expense, expenseDTO);
    }

    public void deleteExpense(long id) {
        Expense expense = expenseRepository.findById(id).orElse(null);

        if (expense == null) {
            throw new EntityNotFoundException("Error - Expense not found or does not exist - " + id);
        }

        expenseRepository.delete(expense);
    }

    private Expense saveOrUpdateExpense(Expense expense, ExpenseDTO expenseDTO) {
        expense.setTitle(expenseDTO.getTitle());
        expense.setDate(expenseDTO.getDate());
        expense.setDescription(expenseDTO.getDescription());
        expense.setAmount(expenseDTO.getAmount());
        expense.setCategory(expenseDTO.getCategory());

        return expenseRepository.save(expense);
    }
}
