package com.frutz.pft.services;

import com.frutz.pft.dto.ExpenseDTO;
import com.frutz.pft.entity.Expense;

import java.util.List;

public interface ExpenseService {

    Expense postExpense(ExpenseDTO expenseDTO);
    List<Expense> getExpenses();
    Expense getExpenseById(long id);
    Expense updateExpense(long id, ExpenseDTO expenseDTO);
    void deleteExpense(long id);
}
