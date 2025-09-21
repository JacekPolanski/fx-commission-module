package com.bank.fx_commission.domain;

import java.math.BigDecimal;

public record SpreadLevel(int threshold, BigDecimal commission) {}
