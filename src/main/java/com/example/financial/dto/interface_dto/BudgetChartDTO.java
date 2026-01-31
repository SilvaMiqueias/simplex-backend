package com.example.financial.dto.interface_dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface BudgetChartDTO {

    Integer getId();
    LocalDateTime getDateReference();
    Integer getCategory();
    BigDecimal getAmount();
    BigDecimal getSpentAmount();
    BigDecimal getRemainingAmount();
    String getDescription();
}
