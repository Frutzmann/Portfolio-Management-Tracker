package com.frutz.pft.dto;

import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;

@Data
public class ExpenseDTO {

    private long id;

    @Getter
    private String title;

    @Getter
    private String description;

    @Getter
    private String category;

    @Getter
    private LocalDate date;

    @Getter
    private Integer amount;

}
