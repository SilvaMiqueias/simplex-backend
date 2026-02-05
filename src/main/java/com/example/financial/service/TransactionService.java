package com.example.financial.service;

import com.example.financial.dto.TransactionDTO;
import com.example.financial.mapper.TransactionMapper;
import com.example.financial.model.Transaction;
import com.example.financial.repository.TransactionRepository;
import com.example.financial.security.utils.AuthenticationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AuthenticationUtil authenticationUtil;

    public List<TransactionDTO> getAllTransactions() {
        return transactionRepository.findAllByUserId_Id(authenticationUtil.getUserLogged().getId()).stream().map(TransactionMapper.INSTANCE::transactionToTransactionDTO).collect(Collectors.toList());
    }

    public TransactionDTO getTransactionById(Integer id) {
        Optional<Transaction> transaction = transactionRepository.findById(id);
        if (transaction.isPresent())
            return transaction.map(TransactionMapper.INSTANCE::transactionToTransactionDTO).orElse(null);
        throw new RuntimeException("Transaction not found");
    }

    public TransactionDTO createTransaction(TransactionDTO transactionDTO) {
        Transaction transaction = TransactionMapper.INSTANCE.transactionDTOToTransaction(transactionDTO);
        transaction.setUserId(authenticationUtil.getUserLogged());
        transaction = transactionRepository.save(transaction);
        return TransactionMapper.INSTANCE.transactionToTransactionDTO(transaction);
    }

    public TransactionDTO updateTransaction(TransactionDTO transactionDTO) {
        Optional<Transaction> transaction = transactionRepository.findById(transactionDTO.getId());
        Transaction transactionToUpdate = null;
        if (transaction.isPresent()) {
            transactionToUpdate = TransactionMapper.INSTANCE.transactionDTOToTransaction(transactionDTO);
            transactionToUpdate.setUserId(authenticationUtil.getUserLogged());
            transactionToUpdate = transactionRepository.save(transactionToUpdate);
        }else{
            throw new RuntimeException("Transaction not found");
        }
        return TransactionMapper.INSTANCE.transactionToTransactionDTO(transactionToUpdate);
    }
    public void deleteTransaction(Integer id) {
        Optional<Transaction> transaction = transactionRepository.findById(id);
        transaction.ifPresent(value -> transactionRepository.delete(value));
    }
}
