package com.example.financial.controller;

import com.example.financial.dto.BudgetResultDTO;
import com.example.financial.dto.GoalDTO;
import com.example.financial.dto.interface_dto.GoalChartDTO;
import com.example.financial.service.GoalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/customer/goal/")
@Tag(name = "Metas do Cliente", description = "Endpoints para gerenciamento de metas do cliente")
public class GoalController {

    @Autowired
    private GoalService goalService;

    @Operation(summary = "Listar todas as metas", description = "Retorna todas as metas do cliente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de metas retornada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    @GetMapping("/find-all")
    public ResponseEntity<List<GoalDTO>> findAll(BudgetResultDTO budgetDTO){
        return   ResponseEntity.ok().body(goalService.getAllGoals());
    }

    @Operation(summary = "Criar meta", description = "Cria uma nova meta para o cliente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Meta criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping("/create")
    public ResponseEntity<GoalDTO> create(@RequestBody GoalDTO goalDTO){
        return   ResponseEntity.ok().body(goalService.createGoal(goalDTO));
    }

    @Operation(summary = "Atualizar meta", description = "Atualiza os dados de uma meta existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Meta atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Meta não encontrada"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PutMapping("/update")
    public ResponseEntity<GoalDTO> update(@RequestBody GoalDTO goalDTO){
        return   ResponseEntity.ok().body(goalService.updateGoal(goalDTO));
    }

    @Operation(summary = "Deletar meta", description = "Deleta uma meta específica pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Meta deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Meta não encontrada")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id){
        goalService.deleteGoal(id);
        return  ResponseEntity.ok().build();
    }

    @Operation(summary = "Listar todas as metas para gráfico", description = "Retorna todas as metas formatadas para gráficos")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de metas para gráfico retornada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    @GetMapping("/find-all-chart")
    public ResponseEntity<List<GoalChartDTO>> findAllChart(BudgetResultDTO budgetDTO) {
        return   ResponseEntity.ok().body(goalService.getAllGoalToChart(budgetDTO));
    }
}
