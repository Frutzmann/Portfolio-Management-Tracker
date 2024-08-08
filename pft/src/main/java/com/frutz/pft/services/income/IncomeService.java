package com.frutz.pft.services.income;

import com.frutz.pft.dto.IncomeDTO;
import com.frutz.pft.entity.Income;

import java.util.List;

public interface IncomeService {

    Income saveOrUpdateIncome(Income income, IncomeDTO iDTO);
    Income getIncomeById(long id);
    List<Income> getAllIncomes();
    Income updateIncome(long id, IncomeDTO iDTO);
    Income postIncome(IncomeDTO iDTO);
    void deleteIncome(long id);
}
