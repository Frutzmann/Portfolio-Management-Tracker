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

    private float supply;

    private float marketCapUsd;

    private float volumeUsd24Hr;

    private float changerPercent24Hr;

    private float priceUsd;
}
