package com.example.financial.repository;

import com.example.financial.model.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BudgetRepository extends JpaRepository<Budget, Integer> {

    List<Budget> findAllByUserId_Id(Long userId);
}
