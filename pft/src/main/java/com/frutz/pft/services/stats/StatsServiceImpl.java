package com.frutz.pft.services.stats;

import com.frutz.pft.dto.GraphDTO;
import com.frutz.pft.dto.StatsDTO;
import com.frutz.pft.entity.Expense;
import com.frutz.pft.entity.Income;
import com.frutz.pft.repository.ExpenseRepository;
import com.frutz.pft.repository.IncomeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {

    private final IncomeRepository incomeRepository;
    private final ExpenseRepository expenseRepository;

    public GraphDTO getChartData() {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(27);

        GraphDTO graphDTO = new GraphDTO();
        graphDTO.setExpenseList(expenseRepository.findByDateBetween(startDate, endDate));
        graphDTO.setIncomeList(incomeRepository.findByDateBetween(startDate, endDate));

        return graphDTO;
    }

    public StatsDTO getStats() {
        StatsDTO statsDTO = new StatsDTO();

        statsDTO.setIncome(calculateTotalIncome());
        statsDTO.setExpense(calculateTotalExpense());
        statsDTO.setLatestIncome(getLatestIncome().orElse(null));
        statsDTO.setLatestExpense(getLatestExpense().orElse(null));
        statsDTO.setBalance(statsDTO.getIncome() - statsDTO.getExpense());
        statsDTO.setMinIncome(findMinIncome());
        statsDTO.setMaxIncome(findMaxIncome());
        statsDTO.setMinExpense(findMinExpense());
        statsDTO.setMaxExpense(findMaxExpense());

        return statsDTO;
    }

    private Double calculateTotalIncome() {
        return incomeRepository.sumAllAmount();
    }

    private Double calculateTotalExpense() {
        return expenseRepository.sumAllAmount();
    }

    private Optional<Income> getLatestIncome() {
        return incomeRepository.findFirstByOrderByDateDesc();
    }

    private Optional<Expense> getLatestExpense() {
        return expenseRepository.findFirstByOrderByDateDesc();
    }

    private Double findMinIncome() {
        List<Income> incomeList = incomeRepository.findAll();
        OptionalDouble minIncome = incomeList.stream().mapToDouble(Income::getAmount).min();
        return minIncome.isPresent() ? minIncome.getAsDouble() : null;
    }

    private Double findMaxIncome() {
        List<Income> incomeList = incomeRepository.findAll();
        OptionalDouble maxIncome = incomeList.stream().mapToDouble(Income::getAmount).max();
        return maxIncome.isPresent() ? maxIncome.getAsDouble() : null;
    }

    private Double findMinExpense() {
        List<Expense> expenseList = expenseRepository.findAll();
        OptionalDouble minExpense = expenseList.stream().mapToDouble(Expense::getAmount).min();
        return minExpense.isPresent() ? minExpense.getAsDouble() : null;
    }

    private Double findMaxExpense() {
        List<Expense> expenseList = expenseRepository.findAll();
        OptionalDouble maxExpense = expenseList.stream().mapToDouble(Expense::getAmount).max();
        return maxExpense.isPresent() ? maxExpense.getAsDouble() : null;
    }
}
