package com.frutz.pft.dto;

import com.frutz.pft.entity.Expense;
import com.frutz.pft.entity.Income;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class GraphDTO {

    private List<Expense> expenseList;

    private List<Income> incomeList;

}
