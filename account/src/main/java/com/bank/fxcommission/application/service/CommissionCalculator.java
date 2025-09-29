package com.bank.fxcommission.application.service;

import com.bank.fxcommission.domain.Account;
import com.bank.fxcommission.domain.strategies.CommissionStrategy;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CommissionCalculator {
  private final CommissionStrategy strategy;

  public BigDecimal calculateCommission(Account account, BigDecimal amount) {
    return this.strategy.calculateCommission(account, amount);
  }
}
