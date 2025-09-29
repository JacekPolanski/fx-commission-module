package com.bank.fxcommission.application.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record AddSpreadLevelToAccountUseCaseDto(UUID id, int threshold, BigDecimal commission) {}
