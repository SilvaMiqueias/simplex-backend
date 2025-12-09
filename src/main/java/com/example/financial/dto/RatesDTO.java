package com.example.financial.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RatesDTO {

    @JsonProperty("AUD")
    private BigDecimal aud;

    @JsonProperty("BGN")
    private BigDecimal bgn;

    @JsonProperty("CAD")
    private BigDecimal cad;

    @JsonProperty("CHF")
    private BigDecimal chf;

    @JsonProperty("CNY")
    private BigDecimal cny;

    @JsonProperty("CZK")
    private BigDecimal czk;

    @JsonProperty("DKK")
    private BigDecimal dkk;

    @JsonProperty("EUR")
    private BigDecimal eur;

    @JsonProperty("GBP")
    private BigDecimal gbp;

    @JsonProperty("HKD")
    private BigDecimal hkd;

    @JsonProperty("HUF")
    private BigDecimal huf;

    @JsonProperty("IDR")
    private BigDecimal idr;

    @JsonProperty("ILS")
    private BigDecimal ils;

    @JsonProperty("INR")
    private BigDecimal inr;

    @JsonProperty("ISK")
    private BigDecimal isk;

    @JsonProperty("JPY")
    private BigDecimal jpy;

    @JsonProperty("KRW")
    private BigDecimal krw;

    @JsonProperty("MXN")
    private BigDecimal mxn;

    @JsonProperty("MYR")
    private BigDecimal myr;

    @JsonProperty("NOK")
    private BigDecimal nok;

    @JsonProperty("NZD")
    private BigDecimal nzd;

    @JsonProperty("PHP")
    private BigDecimal php;

    @JsonProperty("PLN")
    private BigDecimal pln;

    @JsonProperty("RON")
    private BigDecimal ron;

    @JsonProperty("SEK")
    private BigDecimal sek;

    @JsonProperty("SGD")
    private BigDecimal sgd;

    @JsonProperty("THB")
    private BigDecimal thb;

    @JsonProperty("USD")
    private BigDecimal usd;

    @JsonProperty("ZAR")
    private BigDecimal zar;
}
