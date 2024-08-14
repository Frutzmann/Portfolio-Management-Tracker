package com.frutz.pft.repository;

import com.frutz.pft.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {
    List<Position> findByPortfolioId(Long portfolioId);
    List<Position> findByCoinId(String coindId);
}
