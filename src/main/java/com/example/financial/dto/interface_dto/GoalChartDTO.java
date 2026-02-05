package com.example.financial.dto.interface_dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface GoalChartDTO {

    Integer getId();
    Integer getCategory();
    BigDecimal getAmount();
    LocalDateTime getDateStart();
    LocalDateTime getDateEnd();
    BigDecimal getAchievedAmount();
    BigDecimal getRemainingAmount();
    String getDescription();
}
