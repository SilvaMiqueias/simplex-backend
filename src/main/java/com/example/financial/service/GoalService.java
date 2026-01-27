package com.example.financial.service;

import com.example.financial.dto.GoalDTO;
import com.example.financial.mapper.GoalMapper;
import com.example.financial.model.Goal;
import com.example.financial.repository.GoalRepository;
import com.example.financial.repository.TransactionRepository;
import com.example.financial.security.utils.AuthenticationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GoalService {

    @Autowired
    private GoalRepository goalRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AuthenticationUtil authenticationUtil;

    public List<GoalDTO> getAllGoals() {
        Long userId = authenticationUtil.getUserLogged().getId();
        List<Goal> goals = goalRepository.findAllByUserId_Id(userId);
        
        return goals.stream().map(goal -> {
            GoalDTO dto = GoalMapper.INSTANCE.goalToGoalDTO(goal);
            
            // Calcular o valor atual baseado nas transações de receita da categoria
            BigDecimal currentAmount = transactionRepository.sumIncomeByUserAndCategory(
                userId,
                goal.getCategory(),
                goal.getDateStart(),
                goal.getDateEnd()
            );
            dto.setCurrentAmount(currentAmount != null ? currentAmount : BigDecimal.ZERO);
            
            return dto;
        }).collect(Collectors.toList());
    }

    public GoalDTO  createGoal(GoalDTO goalDTO) {
        Goal goal = GoalMapper.INSTANCE.goalDTOToGoal(goalDTO);
        goal.setUserId(authenticationUtil.getUserLogged());
        goal = goalRepository.save(goal);
        return   GoalMapper.INSTANCE.goalToGoalDTO(goal);
    }

    public GoalDTO  updateGoal(GoalDTO goalDTO) {
        Goal goal = GoalMapper.INSTANCE.goalDTOToGoal(goalDTO);
        goal.setUserId(authenticationUtil.getUserLogged());
        goal = goalRepository.save(goal);
        return   GoalMapper.INSTANCE.goalToGoalDTO(goal);
    }

    public void deleteGoal(Integer goalId) {
        Optional<Goal> goal = goalRepository.findById(goalId);
        goal.ifPresent(value -> goalRepository.delete(value));
    }
}
