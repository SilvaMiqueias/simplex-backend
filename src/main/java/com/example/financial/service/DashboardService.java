package com.example.financial.service;

import com.example.financial.dto.DashboardCardResultDTO;
import com.example.financial.dto.interface_dto.DashboardChartDTO;
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

    public DashboardCardResultDTO getInfosToDashboardCardByUser(){
        DashboardCardResultDTO resultDTO = new DashboardCardResultDTO();
        User user = authenticationUtil.getUserLogged();
        LocalDate today = LocalDate.now();

        resultDTO.setCurrentMonth(this.transactionRepository.getInfosToDashboardCardByUser(user.getId(), today.getMonthValue(), today.getYear()));
        LocalDate previousMonth = LocalDate.now().minusMonths(1);
        resultDTO.setPreviousMonth(this.transactionRepository.getInfosToDashboardCardByUser(user.getId(), previousMonth
                .getMonthValue(), previousMonth.getYear()));

        return resultDTO;
    }

    public DashboardCardResultDTO getInfosToDashboardCardByAdmin(){
        DashboardCardResultDTO resultDTO = new DashboardCardResultDTO();
        LocalDate today = LocalDate.now();

        resultDTO.setCurrentMonth(this.transactionRepository.getInfosToDashboardCardByAdmin(today.getMonthValue(), today.getYear()));
        LocalDate previousMonth = LocalDate.now().minusMonths(1);
        resultDTO.setPreviousMonth(this.transactionRepository.getInfosToDashboardCardByAdmin(previousMonth.getMonthValue(), previousMonth.getYear()));

        return resultDTO;
    }

    public List<DashboardChartDTO> getInfosToDashboardChartCustomer(){
        return transactionRepository.getInfosToDashboardChartByCustomer(authenticationUtil.getUserLogged().getId());
    }

    public List<DashboardChartDTO> getInfosToDashboardChartAdmin(){
        return transactionRepository.getInfosToDashboardChartByAdmin();
    }

}
