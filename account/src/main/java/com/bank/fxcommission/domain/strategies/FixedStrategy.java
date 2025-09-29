package com.bank.fxcommission.domain.strategies;

import com.bank.fxcommission.domain.Account;
import com.bank.fxcommission.domain.Spread;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class FixedStrategy implements CommissionStrategy {
  @Override
  public BigDecimal calculateCommission(Account account, BigDecimal amount) {
    final Spread spread = account.getSpread();
    return amount
        .multiply(spread.getSpreadLevels().get(0))
        .divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP);
  }
}
