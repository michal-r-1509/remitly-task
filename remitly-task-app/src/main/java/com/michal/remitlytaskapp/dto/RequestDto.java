package com.michal.remitlytaskapp.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class RequestDto {
    @NotBlank
    @Size(min = 3, max = 3)
    private String baseCurrency;
    @NotBlank
    @Size(min = 3, max = 3)
    private String inputCurrency;
    @NotBlank
    @Size(min = 3, max = 3)
    private String outputCurrency;
    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("999999999.99")
    private BigDecimal value;
}
