package com.example.financial.controller;

import com.example.financial.dto.RatesDetailDTO;
import com.example.financial.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("api/v1/currency")
public class CurrencyController {

    @Autowired
    private CurrencyService currencyService;

    @GetMapping("/rates")
    public ResponseEntity<RatesDetailDTO> getRates() {
        return ResponseEntity.ok().body(currencyService.getRates());
    }

    @GetMapping("/rates-with-variation")
    public ResponseEntity<Map<String, Object>> getRatesWithVariation() {
        return ResponseEntity.ok().body(currencyService.getRatesWithVariation());
    }
}
