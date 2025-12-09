package com.example.financial.repository;

import com.example.financial.model.Goal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GoalRepository extends JpaRepository<Goal, Integer> {
    List<Goal> findAllByUserId_Id(Long userId);
}
