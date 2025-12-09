package com.example.financial.mapper;

import com.example.financial.dto.GoalDTO;
import com.example.financial.model.Goal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GoalMapper {
    GoalMapper INSTANCE = Mappers.getMapper(GoalMapper.class);

    GoalDTO goalToGoalDTO(Goal goal);

    @Mapping(target = "userId", ignore = true)
    Goal  goalDTOToGoal(GoalDTO goalDTO);
}
