package com.example.financial.repository;

import com.example.financial.dto.interface_dto.BudgetChartDTO;
import com.example.financial.model.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface BudgetRepository extends JpaRepository<Budget, Integer> {

    List<Budget> findAllByUserId_Id(Long userId);


    @Query(value = "SELECT * FROM budget bud " +
            "WHERE bud.users_id = :userId " +
            "AND EXTRACT(MONTH FROM bud.date_reference) = :month " +
            "AND EXTRACT(YEAR  FROM bud.date_reference) = :year",
    nativeQuery = true)
    List<Budget> findAllByUserAndDate(@Param("userId") Long userId, @Param("month") Integer month, @Param("year") Integer year);


    @Query(value = """
                SELECT EXISTS (
                    SELECT 1
                    FROM budget bud
                    WHERE bud.users_id = :userId
                      AND EXTRACT(MONTH FROM bud.date_reference) = :month
                      AND EXTRACT(YEAR  FROM bud.date_reference) = :year
                      AND bud.category = :category 
                      AND bud.amount = :amount          
                )
            """, nativeQuery = true)
    boolean existsByUserAndDate(
            @Param("userId") Long userId,
            @Param("month") Integer month,
            @Param("year") Integer year,
            @Param("category") Integer category,
            @Param("amount") BigDecimal amount
    );

    @Query(value= "" +
            "SELECT " +
            "    bud.id AS id, " +
            "    bud.date_reference AS dateReference, " +
            "    bud.category AS category," +
            "    bud.amount    AS amount, " +
            " bud.description as description, " +
            "    COALESCE(SUM(tra.amount), 0) AS spentAmount, " +
            "    (bud.amount - COALESCE(SUM(tra.amount), 0)) AS remainingAmount " +
            "FROM budget bud " +
            "LEFT JOIN transaction tra " +
            "    ON tra.category = bud.category " +
            "   AND tra.users_id = bud.users_id " +
            "   AND tra.transaction_type = 0" +
            "   AND EXTRACT(MONTH FROM tra.date_transaction) = :month" +
            "   AND EXTRACT(YEAR  FROM tra.date_transaction) = :year" +
            "   WHERE bud.users_id = :userId " +
            "  AND EXTRACT(MONTH FROM bud.date_reference) = :month " +
            "  AND EXTRACT(YEAR  FROM bud.date_reference) = :year " +
            "  GROUP BY " +
            "    bud.category, " +
            "    bud.amount," +
            "   bud.id, " +
            "   bud.date_reference;"
    , nativeQuery = true
    )
    List<BudgetChartDTO> findAllBudgetToChart(@Param("userId") Long userId,
                                              @Param("month") Integer month,
                                              @Param("year") Integer year);

}
