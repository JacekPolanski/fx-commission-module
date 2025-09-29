package com.bank.fxcommission.domain.strategies;

import com.bank.fxcommission.domain.Account;
import com.bank.fxcommission.domain.Spread;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.Map;

public class TransactionAmountStrategy implements CommissionStrategy {
  @Override
  public BigDecimal calculateCommission(Account account, BigDecimal amount) {
    final Spread spread = account.getSpread();
    final Map<Integer, BigDecimal> levels = spread.getSpreadLevels();

    final BigDecimal commissionPercent = levels.entrySet().stream()
        .filter(e -> amount.compareTo(BigDecimal.valueOf(e.getKey().longValue())) >= 0)
        .max(Comparator.comparingInt(Map.Entry::getKey))
        .map(Map.Entry::getValue)
        .orElse(BigDecimal.ZERO);

    return amount
        .multiply(commissionPercent)
        .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
  }
}
