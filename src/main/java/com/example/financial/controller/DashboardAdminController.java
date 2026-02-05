package com.example.financial.controller;

import com.example.financial.dto.DashboardCardResultDTO;
import com.example.financial.dto.interface_dto.DashboardChartDTO;
import com.example.financial.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/admin/dashboard")
@Tag(name = "Dashboard Admin", description = "Endpoints de administração do dashboard")
public class DashboardAdminController {

    @Autowired
    private DashboardService dashboardService;

    @Operation(summary = "Informações dos cards do dashboard", description = "Retorna os dados resumidos para exibição nos cards do dashboard do administrador")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Informações dos cards retornadas com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "500", description = "Erro interno ao processar a requisição")
    })
    @GetMapping("/infos-cards")
    public ResponseEntity<DashboardCardResultDTO> infosCards() {
        return ResponseEntity.ok().body(dashboardService.getInfosToDashboardCardByAdmin());
    }


    @Operation(summary = "Informações dos charts do dashboard", description = "Retorna os dados dos gráficos do dashboard do administrador")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Informações dos charts retornadas com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "500", description = "Erro interno ao processar a requisição")
    })
    @GetMapping("/infos-charts")
    public ResponseEntity<List<DashboardChartDTO>> infosCharts() {
        return ResponseEntity.ok().body(dashboardService.getInfosToDashboardChartAdmin());
    }
}
