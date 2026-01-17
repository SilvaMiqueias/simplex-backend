package com.example.financial.controller;

import com.example.financial.dto.DashboardCardResultDTO;
import com.example.financial.dto.interface_dto.DashboardChartDTO;
import com.example.financial.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("api/v1/admin/dashboard")
public class DashboardAdminController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/infos-cards")
    public ResponseEntity<DashboardCardResultDTO> infosCards() {
        return ResponseEntity.ok().body(dashboardService.getInfosToDashboardCardByAdmin());
    }

    @GetMapping("/infos-charts")
    public ResponseEntity<List<DashboardChartDTO>> infosCharts() {
        return ResponseEntity.ok().body(dashboardService.getInfosToDashboardChartAdmin());
    }
}
