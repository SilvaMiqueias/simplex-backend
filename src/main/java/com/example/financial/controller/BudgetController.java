package com.example.financial.controller;

import com.example.financial.dto.BudgetDTO;
import com.example.financial.service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("api/v1/customer/budget/")
public class BudgetController {

    @Autowired
    private BudgetService budgetService;

    @GetMapping("/find-all")
    public ResponseEntity<List<BudgetDTO>> findAll(){
        return   ResponseEntity.ok().body(budgetService.getAllBudgets());
    }

    @PostMapping("/create")
    public ResponseEntity<BudgetDTO> create(@RequestBody BudgetDTO budgetDTO){
        return   ResponseEntity.ok().body(budgetService.createBudget(budgetDTO));
    }
}
