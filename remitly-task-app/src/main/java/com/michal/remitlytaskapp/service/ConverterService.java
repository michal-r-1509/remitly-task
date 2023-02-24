package com.michal.remitlytaskapp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.michal.remitlytaskapp.dto.RequestDto;
import com.michal.remitlytaskapp.dto.ResponseDto;
import com.michal.remitlytaskapp.web_service.CurrencyWebService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@RequiredArgsConstructor
@Service
public class ConverterService {

    private final ObjectMapper objectMapper;
    private final CurrencyWebService currencyWebService;

    public ResponseDto convert(RequestDto data) {
        BigDecimal exchangeRate;
        BigDecimal result;

        if (data.getInputCurrency().equalsIgnoreCase(data.getBaseCurrency())) {
            exchangeRate = currencyWebService.getCurrencyRate(data.getBaseCurrency());
        } else if (data.getOutputCurrency().equalsIgnoreCase(data.getBaseCurrency())) {
            exchangeRate = (BigDecimal.ONE).divide(
                    currencyWebService.getCurrencyRate(data.getBaseCurrency()), 4, RoundingMode.HALF_UP);
        } else {
            throw new IllegalArgumentException("Invalid data");
        }
        result = exchangeRate.multiply(data.getValue());
        return ResponseDto.builder()
                .result(objectMapper
                        .convertValue(result.setScale(2, RoundingMode.CEILING), String.class))
                .exchangeRate(objectMapper.convertValue(exchangeRate, String.class))
                .build();
    }
}
