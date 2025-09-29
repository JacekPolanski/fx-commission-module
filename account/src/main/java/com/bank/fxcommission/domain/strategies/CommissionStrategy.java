package com.bank.fxcommission.domain.strategies;

import com.bank.fxcommission.domain.Account;
import java.math.BigDecimal;

public interface CommissionStrategy {
  BigDecimal calculateCommission(Account account, BigDecimal amount);
}
