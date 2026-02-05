package com.example.financial.service;

import com.example.financial.dto.StockListDTO;
import com.example.financial.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class StockService {

    @Value("${brapi.token}")
    private  String token;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private StockRepository stockRepository;

    final static String url = "https://brapi.dev/api/quote/";

    public List<String> getAllSymbolByStocksActive() {
        return stockRepository.findNameByActiveTrue();
    }

    public StockListDTO getAllStocks(){
        StockListDTO response = new StockListDTO();
        try {
            List<String> symbols = getAllSymbolByStocksActive();
            String urlFinal = url.concat(String.join(",", symbols)).concat("?token=").concat(token);
            response = restTemplate.getForObject(urlFinal, StockListDTO.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }
}
