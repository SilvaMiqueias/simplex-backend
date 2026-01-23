package com.example.financial.dto.interface_dto;

import java.math.BigDecimal;

public interface BudgetChartDTO {

    Integer getCategory();
    BigDecimal getAmount();
    BigDecimal getSpentAmount();
    BigDecimal getRemainingAmount();
}
