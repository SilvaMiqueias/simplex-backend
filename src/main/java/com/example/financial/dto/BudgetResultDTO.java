package com.example.financial.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BudgetResultDTO {
    private LocalDate referenceDate;
}
