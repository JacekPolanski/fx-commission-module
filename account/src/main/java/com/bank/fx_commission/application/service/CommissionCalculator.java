package com.bank.fx_commission.application.service;

import com.bank.fx_commission.domain.strategies.CommissionStrategy;
import com.bank.fx_commission.domain.Account;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class CommissionCalculator {
    private final CommissionStrategy strategy;

    public BigDecimal calculateCommission(Account account, BigDecimal amount) {
        return this.strategy.calculateCommission(account, amount);
    }
}
