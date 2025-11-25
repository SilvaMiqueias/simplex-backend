package com.example.financial.service;

import com.example.financial.dto.interface_dto.DashboardCardDTO;
import com.example.financial.model.User;
import com.example.financial.repository.TransactionRepository;
import com.example.financial.security.utils.AuthenticationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DashboardService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AuthenticationUtil authenticationUtil;

    public List<DashboardCardDTO> getInfosToDashboardCardByUser(){
        User user = authenticationUtil.getUserLogged();
        LocalDate today = LocalDate.now();
       return   this.transactionRepository.getInfosToDashboardCardByUser(user.getId(), today.getMonthValue(), today.getYear());
    }

    public List<DashboardCardDTO> getInfosToDashboardCardByAdmin(){
        LocalDate today = LocalDate.now();
        return   this.transactionRepository.getInfosToDashboardCardByAdmin(today.getMonthValue(), today.getYear());
    }
}
