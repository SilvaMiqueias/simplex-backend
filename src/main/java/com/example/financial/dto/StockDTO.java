package com.example.financial.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockDTO {

    @JsonProperty("symbol")
    private String symbol;

    @JsonProperty("shortName")
    private String shortName;

    @JsonProperty("longName")
    private String longName;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("regularMarketPrice")
    private Double regularMarketPrice;

    @JsonProperty("regularMarketDayHigh")
    private Double regularMarketDayHigh;

    @JsonProperty("regularMarketDayLow")
    private Double regularMarketDayLow;

    @JsonProperty("regularMarketDayRange")
    private String regularMarketDayRange;

    @JsonProperty("regularMarketChange")
    private Double regularMarketChange;

    @JsonProperty("regularMarketChangePercent")
    private Double regularMarketChangePercent;

    @JsonProperty("regularMarketTime")
    private OffsetDateTime regularMarketTime;

    @JsonProperty("marketCap")
    private Long marketCap;

    @JsonProperty("regularMarketVolume")
    private Long regularMarketVolume;

    @JsonProperty("regularMarketPreviousClose")
    private Double regularMarketPreviousClose;

    @JsonProperty("regularMarketOpen")
    private Double regularMarketOpen;

    @JsonProperty("fiftyTwoWeekRange")
    private String fiftyTwoWeekRange;

    @JsonProperty("fiftyTwoWeekLow")
    private Double fiftyTwoWeekLow;

    @JsonProperty("fiftyTwoWeekHigh")
    private Double fiftyTwoWeekHigh;

    @JsonProperty("priceEarnings")
    private Double priceEarnings;

    @JsonProperty("earningsPerShare")
    private Double earningsPerShare;

    @JsonProperty("logourl")
    private String logoUrl;
}
