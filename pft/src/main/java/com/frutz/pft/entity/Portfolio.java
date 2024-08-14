package com.frutz.pft.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.boot.context.properties.bind.DefaultValue;

@Entity
@Data
public class Portfolio
{
    @Id
    @GeneratedValue
    private long id;

    @Column(name = "cash_balance_usd")
    private long cashBalanceUsd;

    private String name;

    private String type;
}
