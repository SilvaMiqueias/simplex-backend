package com.example.financial.service;

import com.example.financial.dto.BudgetDTO;
import com.example.financial.dto.BudgetResultDTO;
import com.example.financial.dto.interface_dto.BudgetChartDTO;
import com.example.financial.mapper.BudgetMapper;
import com.example.financial.model.Budget;
import com.example.financial.model.User;
import com.example.financial.repository.BudgetRepository;
import com.example.financial.security.utils.AuthenticationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<BudgetChartDTO> getAllBudgetsToChart(BudgetResultDTO budgetDTO) {
        return   budgetRepository.findAllBudgetToChart(authenticationUtil.getUserLogged().getId(), budgetDTO.getReferenceDate().getMonthValue(), budgetDTO.getReferenceDate().getYear());
    }

    public BudgetDTO  createBudget(BudgetDTO budgetDTO) {
        Budget budget = BudgetMapper.INSTANCE.budgetDTOToBudget(budgetDTO);
        User user = authenticationUtil.getUserLogged();

        if(existsBudget(user.getId(), budgetDTO.getDateReference().getMonthValue(), budgetDTO.getDateReference().getYear(), budgetDTO.getCategory().ordinal())) {
            throw new RuntimeException("Orçamento já existe!");
        }

        budget.setUserId(user);
        budget = budgetRepository.save(budget);
        return   BudgetMapper.INSTANCE.budgetToBudgetDTO(budget);
    }

    public BudgetDTO  update(BudgetDTO budgetDTO) {
        Budget budget = BudgetMapper.INSTANCE.budgetDTOToBudget(budgetDTO);
        User user = authenticationUtil.getUserLogged();

        //validar se é o mesmo
//        if(existsBudget(user.getId(), budgetDTO.getDateReference().getMonthValue(), budgetDTO.getDateReference().getYear(), budgetDTO.getCategory().ordinal())) {
//            throw new RuntimeException("Orçamento já existe!");
//        }

        budget = budgetRepository.save(budget);
        return   BudgetMapper.INSTANCE.budgetToBudgetDTO(budget);
    }

    public void deleteBudget(Integer idBudget) {
        Optional<Budget> budget = budgetRepository.findById(idBudget);
        budget.ifPresent(value -> budgetRepository.delete(value));
    }

    public Boolean existsBudget(Long id, Integer month, Integer year, Integer category) {
        return budgetRepository.existsByUserAndDate(id, month, year, category);
    }
}
