package com.bank.fx_commission.domain.strategies;

import com.bank.fx_commission.domain.Account;

import java.math.BigDecimal;

public interface CommissionStrategy {
    BigDecimal calculateCommission(Account account, BigDecimal amount);
}
