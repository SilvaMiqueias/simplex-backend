package com.example.financial.repository;

import com.example.financial.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StockRepository extends JpaRepository<Stock, Long> {


    @Query(value = "SELECT stock.symbol FROM Stock stock where stock.active = true")
    List<String> findNameByActiveTrue();
}
