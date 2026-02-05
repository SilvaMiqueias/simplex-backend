package com.example.financial.controller;

import com.example.financial.dto.RatesListDTO;
import com.example.financial.dto.StockListDTO;
import com.example.financial.service.CurrencyService;
import com.example.financial.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/v1/public")
public class PublicController {

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private StockService stockService;

    @GetMapping("/find-all-rates")
    public ResponseEntity<RatesListDTO> getRates(){
        return   ResponseEntity.ok().body(currencyService.getRates());
    }

    @GetMapping("/find-all-stocks")
    public ResponseEntity<StockListDTO> getStocks(){
        return   ResponseEntity.ok().body(stockService.getAllStocks());
    }
}
