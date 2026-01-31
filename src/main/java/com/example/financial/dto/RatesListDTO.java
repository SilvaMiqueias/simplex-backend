package com.example.financial.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RatesListDTO {

    private LocalDate today;
    private LocalDate previous;
    private RatesDTO todayRates;
    private RatesDTO previousRates;
}
