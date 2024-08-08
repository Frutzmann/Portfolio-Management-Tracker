package com.frutz.pft.services.income;

import com.frutz.pft.dto.IncomeDTO;
import com.frutz.pft.entity.Income;
import com.frutz.pft.repository.IncomeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.web.client.HttpMessageConvertersRestClientCustomizer;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IncomeServiceImpl implements IncomeService {

    private final IncomeRepository incomeRepository;
    private final HttpMessageConvertersRestClientCustomizer httpMessageConvertersRestClientCustomizer;

    public Income saveOrUpdateIncome(Income income, IncomeDTO iDTO) {
        income.setTitle(iDTO.getTitle());
        income.setDescription(iDTO.getDescription());
        income.setAmount(iDTO.getAmount());
        income.setDate(iDTO.getDate());
        income.setCategory(iDTO.getCategory());

        return incomeRepository.save(income);

    }

    public Income getIncomeById(long id) {
        Income income = incomeRepository.findById(id).orElse(null);

        if (income == null) {
            throw new EntityNotFoundException("Income not found");
        }
        return income;
    }

    public List<Income> getAllIncomes() {
        return incomeRepository.findAll().stream()
                .sorted(Comparator.comparing(Income::getDate).reversed())
                .collect(Collectors.toList());
    }

    public Income updateIncome(long id, IncomeDTO iDTO) {
        Income income = incomeRepository.findById(id).orElse(null);

        if(income == null)
            throw new EntityNotFoundException("Error - Income not found or does not exist - " + id);

        return saveOrUpdateIncome(income, iDTO);
    }

    public Income postIncome(IncomeDTO iDTO) {
        return saveOrUpdateIncome(new Income(), iDTO);
    }

    @Override
    public void deleteIncome(long id) {
        Income income = incomeRepository.findById(id).orElse(null);

        if(income == null) {
            throw new EntityNotFoundException("Error - Income not found or does not exist - " + id);
        }
        incomeRepository.delete(income);
    }
}
