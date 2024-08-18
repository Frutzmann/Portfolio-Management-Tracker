package com.frutz.pft.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.boot.context.properties.bind.DefaultValue;

@Entity
@Data
public class Portfolio
{
    @Id
    @GeneratedValue
    private long id;

    @OneToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @Column(name = "cash_balance_usd")
    private long cashBalanceUsd;

    private String name;

    private String type;
}
