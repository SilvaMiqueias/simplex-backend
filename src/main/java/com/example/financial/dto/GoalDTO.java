package com.example.financial.dto;

import com.example.financial.model.enumerador.CategoryEnum;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoalDTO {
    private Integer id;
    private CategoryEnum category;
    private BigDecimal amount;
    private BigDecimal currentAmount; // Valor atual economizado/gasto na categoria
    private String description;
    private LocalDateTime dateStart;
    private LocalDateTime dateEnd;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
