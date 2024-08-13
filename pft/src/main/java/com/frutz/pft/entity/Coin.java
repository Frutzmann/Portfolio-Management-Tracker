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
    private float supply;

    @Column(name = "market_cap_usd")
    private float marketCapUsd;

    @Column(name = "volume_usd24hr")
    private float volumeUsd24Hr;

    @Column(name = "change_percent24hr")
    private float changePercent24Hr;

    @Column(name = "price_usd")
    private float priceUsd;
}
