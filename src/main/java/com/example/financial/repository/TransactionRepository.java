package com.example.financial.repository;

import com.example.financial.dto.interface_dto.DashboardCardDTO;
import com.example.financial.dto.interface_dto.DashboardChartDTO;
import com.example.financial.model.Transaction;
import com.example.financial.model.enumerador.CategoryEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {


    @Query(
            value = "SELECT " +
                    "    COALESCE(SUM(CASE WHEN ts.transaction_type = 0 THEN ts.amount ELSE 0 END), 0) AS income," +
                    "    COALESCE(SUM(CASE WHEN ts.transaction_type = 1 THEN ts.amount ELSE 0 END), 0) AS expense," +
                    "    COALESCE(COUNT(ts.id), 0) AS quantity " +
                    "FROM public.users u " +
                    "LEFT JOIN transaction ts " +
                    "       ON ts.users_id = u.id " +
                    "      AND EXTRACT(MONTH FROM ts.date_transaction) = :month " +
                    "      AND EXTRACT(YEAR  FROM ts.date_transaction) = :year " +
                    "WHERE u.id = :userId " +
                    "GROUP BY u.id;",
            nativeQuery = true
    )
    public DashboardCardDTO getInfosToDashboardCardByUser(@Param("userId") Long userId, @Param("month") Integer month, @Param("year") Integer year);


    @Query(
            value = "SELECT " +
                    "    COALESCE(SUM(CASE WHEN ts.transaction_type = 0 THEN ts.amount ELSE 0 END), 0) AS income, " +
                    "    COALESCE(SUM(CASE WHEN ts.transaction_type = 1 THEN ts.amount ELSE 0 END), 0) AS expense, " +
                    "    COALESCE(COUNT(ts.id), 0) AS quantity " +
                    "FROM transaction ts " +
                    "WHERE EXTRACT(MONTH FROM ts.date_transaction) = :month " +
                    "  AND EXTRACT(YEAR  FROM ts.date_transaction) = :year ",
            nativeQuery = true
    )
    public DashboardCardDTO getInfosToDashboardCardByAdmin( @Param("month") Integer month, @Param("year") Integer year);


    @Query(
            value = "WITH meses AS ( " +
                    "  SELECT " +
                    "    date_trunc( " +
                    "      'month', " +
                    "      CURRENT_DATE - INTERVAL '5 months' " +
                    "    ) + (INTERVAL '1 month' * gs) AS months " +
                    "  FROM generate_series(0, 5) gs " +
                    ") " +
                    "SELECT " +
                    "  m.months AS months, " +
                    "  COALESCE(SUM(CASE WHEN ts.transaction_type = 0 THEN ts.amount END), 0) AS income, " +
                    "  COALESCE(SUM(CASE WHEN ts.transaction_type = 1 THEN ts.amount END), 0) AS expense " +
                    "FROM meses m " +
                    "LEFT JOIN transaction ts " +
                    "  ON date_trunc('month', ts.date_transaction) = m.months " +
                    "  AND ts.users_id = :userId " +
                    "GROUP BY m.months " +
                    "ORDER BY m.months ",
            nativeQuery = true
    )
    List<DashboardChartDTO> getInfosToDashboardChartByCustomer(@Param("userId") Long userId);


    @Query(
            value = "WITH meses AS ( " +
                    "  SELECT " +
                    "    date_trunc( " +
                    "      'month', " +
                    "      CURRENT_DATE - INTERVAL '5 months' " +
                    "    ) + (INTERVAL '1 month' * gs) AS months " +
                    "  FROM generate_series(0, 5) gs " +
                    ") " +
                    "SELECT " +
                    "  m.months AS months, " +
                    "  COALESCE(SUM(CASE WHEN ts.transaction_type = 0 THEN ts.amount END), 0) AS income, " +
                    "  COALESCE(SUM(CASE WHEN ts.transaction_type = 1 THEN ts.amount END), 0) AS expense " +
                    "FROM meses m " +
                    "LEFT JOIN transaction ts " +
                    "  ON date_trunc('month', ts.date_transaction) = m.months " +
                    "GROUP BY m.months " +
                    "ORDER BY m.months ",
            nativeQuery = true
    )
    List<DashboardChartDTO> getInfosToDashboardChartByAdmin();

    // Soma receitas (INCOME) de uma categoria para um usuário em um período
    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t " +
           "WHERE t.userId.id = :userId " +
           "AND t.category = :category " +
           "AND t.transactionType = 0 " +
           "AND (:dateStart IS NULL OR t.dateTransaction >= :dateStart) " +
           "AND (:dateEnd IS NULL OR t.dateTransaction <= :dateEnd)")
    BigDecimal sumIncomeByUserAndCategory(
            @Param("userId") Long userId,
            @Param("category") CategoryEnum category,
            @Param("dateStart") LocalDateTime dateStart,
            @Param("dateEnd") LocalDateTime dateEnd
    );
}
