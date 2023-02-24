package com.michal.remitlytaskapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.michal.remitlytaskapp.dto.RequestDto;
import com.michal.remitlytaskapp.service.ConverterService;
import com.michal.remitlytaskapp.web_service.CurrencyWebService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.Mockito.when;

public class ConverterServiceTest {

    @Mock
    private RequestDto requestDto;
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private CurrencyWebService currencyWebService;
    private ConverterService converterService;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
        converterService = new ConverterService(objectMapper, currencyWebService);
    }

    @Test
    @DisplayName("throws IllegalArgumentException when get three different currency codes")
    void convert_throwsIllegalArgumentException(){
        when(requestDto.getBaseCurrency()).thenReturn("PLN");
        when(requestDto.getInputCurrency()).thenReturn("GBP");
        when(requestDto.getOutputCurrency()).thenReturn("USD");
        var exception = catchThrowable(() -> converterService.convert(requestDto));
        assertThat(exception).isInstanceOf(IllegalArgumentException.class);
    }

}
