package com.bank.fx_commission.domain.strategies;

import com.bank.fx_commission.domain.Account;
import com.bank.fx_commission.domain.Spread;

import java.math.BigDecimal;

public class FixedStrategy implements CommissionStrategy{
    @Override
    public BigDecimal calculateCommission(Account account, BigDecimal amount) {
        Spread spread = account.getSpread();
        return amount.multiply(spread.getSpreadLevels().get(0));
    }
}
