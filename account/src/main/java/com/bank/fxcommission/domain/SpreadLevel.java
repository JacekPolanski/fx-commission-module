package com.bank.fxcommission.domain;

import java.math.BigDecimal;

public record SpreadLevel(int threshold, BigDecimal commission) {}
