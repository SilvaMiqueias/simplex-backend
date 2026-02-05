package com.example.financial.controller;

import com.example.financial.dto.RatesListDTO;
import com.example.financial.dto.StockListDTO;
import com.example.financial.service.CurrencyService;
import com.example.financial.service.StockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/public")
@Tag(name = "Serviços Públicos", description = "Endpoints públicos para consulta de câmbio e ações")
public class PublicController {

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private StockService stockService;

    @Operation(summary = "Consultar todas as taxas de câmbio", description = "Retorna a lista completa de taxas de câmbio disponíveis")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de taxas retornada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno ao processar a requisição")
    })
    @GetMapping("/find-all-rates")
    public ResponseEntity<RatesListDTO> getRates(){
        return   ResponseEntity.ok().body(currencyService.getRates());
    }

    @Operation(summary = "Consultar todas as ações", description = "Retorna a lista completa de ações disponíveis")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de ações retornada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno ao processar a requisição")
    })
    @GetMapping("/find-all-stocks")
    public ResponseEntity<StockListDTO> getStocks(){
        return   ResponseEntity.ok().body(stockService.getAllStocks());
    }
}
