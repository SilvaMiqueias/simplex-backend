package com.example.financial.service;

import com.example.financial.dto.RatesDTO;
import com.example.financial.dto.RatesDetailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
public class CurrencyService {

    @Autowired
    private RestTemplate restTemplate;

    public RatesDetailDTO getRates() {
        LocalDate today = LocalDate.now();
        today = getPreviousBusinessDay(today);

        LocalDate yesterday = today.minusDays(1);
        yesterday = getPreviousBusinessDay(yesterday);

        String url = "https://api.frankfurter.dev/v1/".concat(yesterday.toString()).concat("..").concat(today.toString()).concat("?base=BRL");
        RatesDetailDTO response = restTemplate.getForObject(url, RatesDetailDTO.class);

        return response;
    }

    public Map<String, Object> getRatesWithVariation() {
        LocalDate today = LocalDate.now();
        today = getPreviousBusinessDay(today);

        LocalDate yesterday = today.minusDays(1);
        yesterday = getPreviousBusinessDay(yesterday);

        String url = "https://api.frankfurter.dev/v1/".concat(yesterday.toString()).concat("..").concat(today.toString()).concat("?base=BRL");
        RatesDetailDTO response = restTemplate.getForObject(url, RatesDetailDTO.class);

        Map<String, Object> result = new HashMap<>();
        if (response != null && response.getRates() != null) {
            RatesDTO yesterdayRates = response.getRates().get(yesterday.toString());
            RatesDTO todayRates = response.getRates().get(today.toString());
            
            result.put("base", response.getBase());
            result.put("date", today.toString());
            result.put("rates", todayRates);
            result.put("previousRates", yesterdayRates);
        }
        
        return result;
    }

    public static LocalDate getPreviousBusinessDay(LocalDate date) {

        while (date.getDayOfWeek() == DayOfWeek.SATURDAY ||
                date.getDayOfWeek() == DayOfWeek.SUNDAY) {
            date = date.minusDays(1);
        }

        return date;
    }
}
