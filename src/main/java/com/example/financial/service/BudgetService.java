package com.example.financial.service;

import com.example.financial.dto.BudgetDTO;
import com.example.financial.mapper.BudgetMapper;
import com.example.financial.model.Budget;
import com.example.financial.repository.BudgetRepository;
import com.example.financial.security.utils.AuthenticationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BudgetService {

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private AuthenticationUtil authenticationUtil;


    public List<BudgetDTO> getAllBudgets() {
        List<Budget> budgets = budgetRepository.findAllByUserId_Id(authenticationUtil.getUserLogged().getId());
        return budgets.stream().map(BudgetMapper.INSTANCE::budgetToBudgetDTO).collect(Collectors.toList());
    }

    public BudgetDTO  createBudget(BudgetDTO budgetDTO) {
        Budget budget = BudgetMapper.INSTANCE.budgetDTOToBudget(budgetDTO);
        budget.setUserId(authenticationUtil.getUserLogged());
        budget = budgetRepository.save(budget);
        return   BudgetMapper.INSTANCE.budgetToBudgetDTO(budget);
    }

    public BudgetDTO  update(BudgetDTO budgetDTO) {
        Budget budget = BudgetMapper.INSTANCE.budgetDTOToBudget(budgetDTO);
        budget.setUserId(authenticationUtil.getUserLogged());
        budget = budgetRepository.save(budget);
        return   BudgetMapper.INSTANCE.budgetToBudgetDTO(budget);
    }

    public void deleteBudget(Integer idBudget) {
        Optional<Budget> budget = budgetRepository.findById(idBudget);
        budget.ifPresent(value -> budgetRepository.delete(value));
    }
}
