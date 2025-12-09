package com.example.financial.dto;

import com.example.financial.model.User;
import com.example.financial.model.enumerador.CategoryEnum;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BudgetDTO {
    private Integer id;
    private CategoryEnum category;
    private BigDecimal amount;
    private String description;
    private LocalDateTime date_reference;
    private User userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
