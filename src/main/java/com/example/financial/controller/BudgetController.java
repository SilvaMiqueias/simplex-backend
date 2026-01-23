package com.example.financial.controller;

import com.example.financial.dto.BudgetDTO;
import com.example.financial.dto.BudgetResultDTO;
import com.example.financial.dto.interface_dto.BudgetChartDTO;
import com.example.financial.service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("api/v1/customer/budget/")
public class BudgetController {

    @Autowired
    private BudgetService budgetService;


    @GetMapping("/find-all")
    public ResponseEntity<List<BudgetChartDTO>> findAll(BudgetResultDTO budgetDTO) {
        return ResponseEntity.ok().body(budgetService.getAllBudgetsToChart(budgetDTO));
    }

    @GetMapping("/find-all-chart")
    public ResponseEntity<List<BudgetChartDTO>> findAllChart(BudgetResultDTO budgetDTO) {
        return   ResponseEntity.ok().body(budgetService.getAllBudgetsToChart(budgetDTO));
    }

    @PostMapping("/create")
    public ResponseEntity<BudgetDTO> create(@RequestBody BudgetDTO budgetDTO){
        return   ResponseEntity.ok().body(budgetService.createBudget(budgetDTO));
    }

    @PutMapping("/update")
    public ResponseEntity<BudgetDTO> update(@RequestBody BudgetDTO budgetDTO){
        return   ResponseEntity.ok().body(budgetService.update(budgetDTO));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam("id") int id){
        budgetService.deleteBudget(id);
        return  ResponseEntity.ok().build();
    }
}
