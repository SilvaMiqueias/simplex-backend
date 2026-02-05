package com.example.financial.controller;

import com.example.financial.dto.TransactionDTO;
import com.example.financial.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/customer/transaction")
@Tag(name = "Transações Cliente", description = "Endpoints de transações para clientes")
public class TransactionCustomerController {

    @Autowired
    private TransactionService transactionService;

    @Operation(summary = "Listar todas as transações do cliente", description = "Retorna todas as transações realizadas pelo cliente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de transações retornada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "500", description = "Erro interno ao processar a requisição")
    })
    @GetMapping("/find-all")
    public ResponseEntity<List<TransactionDTO>> findAll(){
        return   ResponseEntity.ok().body(transactionService.getAllTransactions());
    }

    @Operation(summary = "Buscar transação por ID", description = "Retorna os detalhes de uma transação específica pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Transação encontrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Transação não encontrada"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    @GetMapping("/find-by-id/{id}")
    public ResponseEntity<TransactionDTO> findById(@PathVariable("id") int id){
        return   ResponseEntity.ok().body(transactionService.getTransactionById(id));
    }

    @Operation(summary = "Criar nova transação", description = "Cria uma nova transação para o cliente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Transação criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    @PostMapping("/create")
    public ResponseEntity<TransactionDTO> create(@RequestBody TransactionDTO transactionDTO){
        return   ResponseEntity.ok().body(transactionService.createTransaction(transactionDTO));
    }

    @Operation(summary = "Atualizar transação", description = "Atualiza os dados de uma transação existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Transação atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Transação não encontrada"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    @PutMapping("/update")
    public ResponseEntity<TransactionDTO> update(@RequestBody TransactionDTO transactionDTO){
        return   ResponseEntity.ok().body(transactionService.updateTransaction(transactionDTO));
    }

    @Operation(summary = "Deletar transação", description = "Deleta uma transação específica pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Transação deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Transação não encontrada"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        transactionService.deleteTransaction(id);
        return  ResponseEntity.ok().build();
    }
}
