package com.frutz.pft.repository;

import com.frutz.pft.entity.Expense;
import com.frutz.pft.entity.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {

    List<Income> findByDateBetween(LocalDate startDate, LocalDate endDate);

    @Query("SELECT SUM(e.amount) FROM Income e")
    Double sumAllAmount();

    Optional<Income> findFirstByOrderByDateDesc();
}
