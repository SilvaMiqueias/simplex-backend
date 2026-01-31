package com.example.financial.service;

import com.example.financial.dto.RatesDTO;
import com.example.financial.dto.RatesDetailDTO;
import com.example.financial.dto.RatesListDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.DayOfWeek;
import java.time.LocalDate;

@Service
public class CurrencyService {

    @Autowired
    private RestTemplate restTemplate;

    public RatesListDTO getRates() {
        LocalDate today = LocalDate.now();
        today = getPreviousBusinessDay(today);

        LocalDate yesterday = today.minusDays(1);
        yesterday = getPreviousBusinessDay(yesterday);

        String url = "https://api.frankfurter.dev/v1/".concat(yesterday.toString()).concat("..").concat(today.toString()).concat("?base=BRL");
        RatesDetailDTO response = restTemplate.getForObject(url, RatesDetailDTO.class);

        assert response != null;
        RatesDTO yesterdayRates = response.getRates().get(yesterday.toString());
        RatesDTO todayRates = response.getRates().get(today.toString());

        return new RatesListDTO(today, yesterday, todayRates, yesterdayRates);
    }

    public static LocalDate getPreviousBusinessDay(LocalDate date) {

        while (date.getDayOfWeek() == DayOfWeek.SATURDAY ||
                date.getDayOfWeek() == DayOfWeek.SUNDAY) {
            date = date.minusDays(1);
        }

        return date;
    }
}
