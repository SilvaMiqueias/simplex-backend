package com.example.financial.mapper;

import com.example.financial.dto.BudgetDTO;
import com.example.financial.model.Budget;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BudgetMapper {
    BudgetMapper INSTANCE = Mappers.getMapper(BudgetMapper.class);

    @Mapping(target = "userId", ignore = true)
    BudgetDTO budgetToBudgetDTO(Budget budget);

    @Mapping(target = "userId", ignore = true)
    Budget  budgetDTOToBudget(BudgetDTO budgetDTO);
}
