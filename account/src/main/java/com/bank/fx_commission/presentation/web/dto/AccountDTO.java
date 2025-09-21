package com.bank.fx_commission.presentation.web.dto;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

public record AccountDTO(UUID id, String name, String iban, String currency, String balance, Map<Integer, BigDecimal> levels) {}
