package com.example.financial.repository;

import com.example.financial.dto.interface_dto.GoalChartDTO;
import com.example.financial.model.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface GoalRepository extends JpaRepository<Goal, Integer> {
    List<Goal> findAllByUserId_Id(Long userId);

    @Query(value = """
            SELECT
                go.id AS id,
                go.category AS category,
                go.amount AS amount,
                COALESCE(SUM(tr.amount), 0) AS achievedAmount,
                go.date_start AS dateStart,
                 go.date_end AS dateEnd,   
                     go.description AS description,
                (go.amount - COALESCE(SUM(tr.amount), 0)) AS remainingAmount
            FROM goal go
            LEFT JOIN transaction tr
                ON tr.category = go.category
               AND tr.users_id = go.users_id
               AND tr.transaction_type = 0
               AND tr.date_transaction >= go.date_start
               AND tr.date_transaction <= :referenceDate
               AND (
                    go.date_end IS NULL
                    OR :referenceDate <= go.date_end
               )
            WHERE go.users_id = :userId
            GROUP BY
                go.id,
                go.category,
                go.amount
    """,
            nativeQuery = true)
    List<GoalChartDTO> findAllGoalChartDTO(
            @Param("userId") Long userId,
            @Param("referenceDate") LocalDate referenceDate
    );

    @Query(value = """
    SELECT EXISTS (
        SELECT 1
        FROM goal go
        WHERE go.users_id = :userId
          AND go.category = :category
          AND go.date_start <= :referenceDate
          AND (
                go.date_end IS NULL
                OR go.date_end >= :referenceDate
          )
    )
""", nativeQuery = true)
    boolean existsGoalByUserCategoryAndPeriod(
            @Param("userId") Long userId,
            @Param("category") Integer category,
            @Param("referenceDate") LocalDateTime referenceDate
    );
}
