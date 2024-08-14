package com.frutz.pft.dto;

import com.frutz.pft.entity.Coin;
import com.frutz.pft.entity.Portfolio;
import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;

@Data
public class PositionDTO {
    private long id;

    @Getter
    private Coin coin;

    @Getter
    private long numberOfCoins;

    @Getter
    private Portfolio portfolio;

    @Getter
    private BigDecimal amoundUsd;
}
