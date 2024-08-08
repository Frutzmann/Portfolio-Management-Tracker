package com.frutz.pft.repository;

import com.frutz.pft.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByDateBetween(LocalDate startDate, LocalDate endDate);

    @Query("SELECT SUM(i.amount) FROM Expense i")
    Double sumAllAmount();

    Optional<Expense> findFirstByOrderByDateDesc();
}
