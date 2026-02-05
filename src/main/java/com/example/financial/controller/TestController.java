package com.example.financial.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/test")
@Tag(name = "Testes de Autenticação", description = "Endpoints para testes de autenticação")
public class TestController {

    @Operation(summary = "Testar endpoint autenticado", description = "Retorna uma mensagem de teste para verificar se a autenticação está funcionando")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Endpoint acessado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    @GetMapping("/test")
    public ResponseEntity<String> test(){
      return   new ResponseEntity<>("Testando point auth", HttpStatus.OK);
    }
}
