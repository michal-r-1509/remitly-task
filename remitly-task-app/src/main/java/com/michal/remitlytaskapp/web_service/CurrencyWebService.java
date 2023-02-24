package com.michal.remitlytaskapp.web_service;

import com.michal.remitlytaskapp.exceptions.DataNotFoundException;
import com.michal.remitlytaskapp.web_service.dto.RateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Slf4j
@RequiredArgsConstructor
@Component
public class CurrencyWebService {

    private final RestTemplate restTemplate;

    private static final String NBP_URL = "http://api.nbp.pl/api/exchangerates/rates/";

    @Cacheable(value = "CurrencyRate")
    public BigDecimal getCurrencyRate(String currency) {
        RateDto rateDto =
                restTemplate.getForObject(NBP_URL + "{table}/{code}/", RateDto.class, "a", currency);
        if (rateDto.getRates() != null) {
            BigDecimal result = rateDto.getRates().stream().findFirst()
                    .orElseThrow(() -> new DataNotFoundException("Exchange rate not found")).getMid();
            log.info("got exchange rate " + result);
            return result;
        }
        throw new DataNotFoundException("Cannot get exchange rate");
    }

}
