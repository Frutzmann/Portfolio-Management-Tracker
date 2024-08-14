package com.frutz.pft.dto;

import com.frutz.pft.entity.Coin;
import com.frutz.pft.entity.Portfolio;

import java.math.BigDecimal;

public class CoinPortfolioDTO {
    private long id;
    private Coin coin;
    private Portfolio portfolio;
    private BigDecimal amoundUsd;
}
