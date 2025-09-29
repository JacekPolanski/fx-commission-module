package com.bank.fxcommission.presentation.web.dto;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

public record AccountDto(
    UUID id,
    String name,
    String iban,
    String currency,
    String balance,
    Map<Integer, BigDecimal> levels) {}
