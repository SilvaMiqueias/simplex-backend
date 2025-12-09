package com.example.financial.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RatesDetailDTO {

    @JsonProperty("amount")
    private BigDecimal amount;

    @JsonProperty("base")
    private String base;

    @JsonProperty("start_date")
    private LocalDate startDate;

    @JsonProperty("end_date")
    private LocalDate endDate;

    @JsonProperty("rates")
    private Map<String, RatesDTO> rates;



}
