package com.frutz.pft.entity;

import com.frutz.pft.dto.IncomeDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Income {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private Integer amount;
    private LocalDate date;
    private String category;
    private String description;

    public IncomeDTO getIncomeDTO() {
        IncomeDTO iDTO = new IncomeDTO();
        iDTO.setId(id);
        iDTO.setTitle(title);
        iDTO.setAmount(amount);
        iDTO.setDate(date);
        iDTO.setCategory(category);
        iDTO.setDescription(description);

        return iDTO;
    }
}
