package com.example.financial.controller;

import com.example.financial.dto.TransactionDTO;
import com.example.financial.service.TransactionService;
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
@RequestMapping("api/v1/admin/transaction")
@Tag(name = "Transações Admin", description = "Endpoints de transações para administradores")
public class TransactionAdminController {

    @Autowired
    private TransactionService transactionService;

    @Operation(summary = "Listar todas as transações", description = "Retorna a lista completa de todas as transações realizadas")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de transações retornada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "500", description = "Erro interno ao processar a requisição")
    })
    @GetMapping("/find-all")
    public ResponseEntity<List<TransactionDTO>> findAll(){
        return   ResponseEntity.ok().body(transactionService.getAllTransactions());
    }
}
