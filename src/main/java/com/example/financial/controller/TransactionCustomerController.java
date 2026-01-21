package com.example.financial.controller;

import com.example.financial.dto.TransactionDTO;
import com.example.financial.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("api/v1/customer/transaction")
public class TransactionCustomerController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/find-all")
    public ResponseEntity<List<TransactionDTO>> findAll(){
        return   ResponseEntity.ok().body(transactionService.getAllTransactions());
    }

    @GetMapping("/find-by-id/{id}")
    public ResponseEntity<TransactionDTO> findById(@PathVariable("id") int id){
        return   ResponseEntity.ok().body(transactionService.getTransactionById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<TransactionDTO> create(@RequestBody TransactionDTO transactionDTO){
        return   ResponseEntity.ok().body(transactionService.createTransaction(transactionDTO));
    }

    @PutMapping("/update")
    public ResponseEntity<TransactionDTO> update(@RequestBody TransactionDTO transactionDTO){
        return   ResponseEntity.ok().body(transactionService.updateTransaction(transactionDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        transactionService.deleteTransaction(id);
        return  ResponseEntity.ok().build();
    }
}
