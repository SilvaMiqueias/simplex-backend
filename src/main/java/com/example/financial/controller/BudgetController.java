package com.example.financial.controller;

import com.example.financial.dto.BudgetDTO;
import com.example.financial.dto.BudgetResultDTO;
import com.example.financial.dto.interface_dto.BudgetChartDTO;
import com.example.financial.service.BudgetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/customer/budget/")
@Tag(name = "Orçamentos Cliente", description = "Endpoints para gerenciamento de orçamentos do cliente")
public class BudgetController {

    @Autowired
    private BudgetService budgetService;


    @Operation(summary = "Buscar orçamento por ID", description = "Retorna os dados de um orçamento específico pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Orçamento encontrado"),
            @ApiResponse(responseCode = "404", description = "Orçamento não encontrado")
    })
    @GetMapping("/find-by")
    public ResponseEntity<BudgetDTO> findBy(@RequestParam Integer id) {
        return ResponseEntity.ok().body(budgetService.getBudget(id));
    }


    @Operation(summary = "Listar todos os orçamentos", description = "Retorna todos os orçamentos de acordo com os filtros fornecidos")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de orçamentos retornada com sucesso")
    })
    @GetMapping("/find-all")
    public ResponseEntity<List<BudgetChartDTO>> findAll(BudgetResultDTO budgetDTO) {
        return ResponseEntity.ok().body(budgetService.getAllBudgetsToChart(budgetDTO));
    }

    @Operation(summary = "Listar todos os orçamentos para gráfico", description = "Retorna os orçamentos formatados para gráficos de análise")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de orçamentos para gráfico retornada com sucesso")
    })
    @GetMapping("/find-all-chart")
    public ResponseEntity<List<BudgetChartDTO>> findAllChart(BudgetResultDTO budgetDTO) {
        return   ResponseEntity.ok().body(budgetService.getAllBudgetsToChart(budgetDTO));
    }

    @Operation(summary = "Criar orçamento", description = "Cria um novo orçamento com os dados fornecidos")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Orçamento criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping("/create")
    public ResponseEntity<BudgetDTO> create(@RequestBody BudgetDTO budgetDTO){
        return   ResponseEntity.ok().body(budgetService.createBudget(budgetDTO));
    }

    @Operation(summary = "Atualizar orçamento", description = "Atualiza os dados de um orçamento existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Orçamento atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Orçamento não encontrado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PutMapping("/update")
    public ResponseEntity<BudgetDTO> update(@RequestBody BudgetDTO budgetDTO){
        return   ResponseEntity.ok().body(budgetService.update(budgetDTO));
    }

    @Operation(summary = "Deletar orçamento", description = "Deleta um orçamento específico pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Orçamento deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Orçamento não encontrado")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id){
        budgetService.deleteBudget(id);
        return  ResponseEntity.ok().build();
    }
}
