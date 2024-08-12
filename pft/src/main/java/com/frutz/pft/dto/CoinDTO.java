package com.frutz.pft.dto;

import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class CoinDTO {

    private String id;

    private String rank;

    private String symbol;

    private String name;

    private long supply;

    private long marketCapUsd;

    private long volumeUsd24Hr;

    private long changerPercent24Hr;

    private long priceUsd;
}
