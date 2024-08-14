package com.frutz.pft.dto;

import lombok.Data;
import lombok.Getter;

@Data
public class PortfolioDTO {
    private long id;

    @Getter
    private long cash_balance_usd;

    @Getter
    private String name;

    @Getter
    private String type;

}
