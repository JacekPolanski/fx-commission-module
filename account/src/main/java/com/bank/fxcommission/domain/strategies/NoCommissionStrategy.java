package com.bank.fxcommission.domain.strategies;

import com.bank.fxcommission.domain.Account;
import java.math.BigDecimal;

public class NoCommissionStrategy implements CommissionStrategy {
  @Override
  public BigDecimal calculateCommission(Account account, BigDecimal amount) {
    return BigDecimal.ZERO;
  }
}
