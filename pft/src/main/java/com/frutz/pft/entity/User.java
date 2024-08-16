package com.frutz.pft.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "pft_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;
}
