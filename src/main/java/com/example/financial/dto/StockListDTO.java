package com.example.financial.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockListDTO {

    @JsonProperty("results")
    private List<StockDTO> results;
}
