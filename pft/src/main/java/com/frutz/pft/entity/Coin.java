package com.frutz.pft.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Coin {

    @Id
    private String id;
    private String rank;
    private String symbol;
    private String name;
    private long supply;

    private long marketCapUsd;

    @Column(name = "volume_usd24hr")
    private long volumeUsd24Hr;

    @Column(name = "change_percent24hr")
    private long changePercent24Hr;

    private long priceUsd;
}
