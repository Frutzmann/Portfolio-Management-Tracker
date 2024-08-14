package com.frutz.pft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoinPortfolio extends JpaRepository<CoinPortfolio, Long> {
}
