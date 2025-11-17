package com.example.financial.controller;

import com.example.financial.dto.TransactionDTO;
import com.example.financial.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("api/v1/admin/transaction")
public class TransactionAdminController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/find-all")
    public ResponseEntity<List<TransactionDTO>> findAll(){
        return   ResponseEntity.ok().body(transactionService.getAllTransactions());
    }
}
