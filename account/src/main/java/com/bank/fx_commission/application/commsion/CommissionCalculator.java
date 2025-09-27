package com.bank.fx_commission.application.commsion;

import com.bank.fx_commission.domain.strategies.CommissionStrategy;
import com.bank.fx_commission.domain.Account;

import java.math.BigDecimal;

public class CommissionCalculator {
    private final CommissionStrategy strategy;

    public CommissionCalculator(CommissionStrategy strategy) {
        this.strategy = strategy;
    }

    public BigDecimal calculateCommission(Account account, BigDecimal amount) {
        return this.strategy.calculateCommission(account, amount);
    }
}
