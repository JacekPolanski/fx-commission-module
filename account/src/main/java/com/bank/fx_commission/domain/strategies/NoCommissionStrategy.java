package com.bank.fx_commission.domain.strategies;

import com.bank.fx_commission.domain.Account;

import java.math.BigDecimal;

public class NoCommissionStrategy implements CommissionStrategy{
    @Override
    public BigDecimal calculateCommission(Account account, BigDecimal amount) {
        return BigDecimal.ZERO;
    }
}
