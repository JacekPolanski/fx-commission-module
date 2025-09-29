package com.bank.fxcommission.application.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record CalculateCommissionUseCaseDto(UUID accountId, BigDecimal amount) {}
