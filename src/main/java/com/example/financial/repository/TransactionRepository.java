package com.example.financial.repository;

import com.example.financial.dto.interface_dto.DashboardCardDTO;
import com.example.financial.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
    public List<DashboardCardDTO> getInfosToDashboardCardByUser(@Param("userId") Long userId, @Param("month") Integer month, @Param("year") Integer year);


    @Query(
            value = "SELECT " +
                    "    COALESCE(SUM(CASE WHEN ts.transaction_type = 0 THEN ts.amount ELSE 0 END), 0) AS income, " +
                    "    COALESCE(SUM(CASE WHEN ts.transaction_type = 1 THEN ts.amount ELSE 0 END), 0) AS expense, " +
                    "    COALESCE(COUNT(ts.id), 0) AS quantity " +
                    "FROM transaction ts " +
                    "WHERE EXTRACT(MONTH FROM ts.date_transaction) = :month " +
                    "  AND EXTRACT(YEAR  FROM ts.date_transaction) = :year " +
                    "GROUP BY ts.users_id;",
            nativeQuery = true
    )
    public List<DashboardCardDTO> getInfosToDashboardCardByAdmin( @Param("month") Integer month, @Param("year") Integer year);
}
