package com.frutz.pft.dto;

import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;

@Data
public class IncomeDTO {

    private Long id;

    @Getter
    private String title;

    @Getter
    private Integer amount;

    @Getter
    private LocalDate date;

    @Getter
    private String category;

    @Getter
    private String description;
}
