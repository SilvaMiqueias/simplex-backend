package com.example.financial.controller;

import com.example.financial.dto.GoalDTO;
import com.example.financial.service.GoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("api/v1/customer/goal/")
public class GoalController {

    @Autowired
    private GoalService goalService;

    @GetMapping("/find-all")
    public ResponseEntity<List<GoalDTO>> findAll(){
        return   ResponseEntity.ok().body(goalService.getAllGoals());
    }

    @PostMapping("/create")
    public ResponseEntity<GoalDTO> create(@RequestBody GoalDTO goalDTO){
        return   ResponseEntity.ok().body(goalService.createGoal(goalDTO));
    }

    @PutMapping("/update")
    public ResponseEntity<GoalDTO> update(@RequestBody GoalDTO goalDTO){
        return   ResponseEntity.ok().body(goalService.updateGoal(goalDTO));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam("id") int id){
        goalService.deleteGoal(id);
        return  ResponseEntity.ok().build();
    }
}
