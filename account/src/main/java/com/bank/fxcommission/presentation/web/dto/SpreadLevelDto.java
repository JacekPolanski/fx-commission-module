package com.bank.fxcommission.presentation.web.dto;

import java.math.BigDecimal;

public record SpreadLevelDto(int threshold, BigDecimal commission) {}
