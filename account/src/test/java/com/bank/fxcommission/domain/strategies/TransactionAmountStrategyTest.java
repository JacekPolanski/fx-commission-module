package com.bank.fxcommission.domain.strategies;

import com.bank.fxcommission.domain.Account;
import com.bank.fxcommission.domain.Spread;
import com.bank.fxcommission.domain.SpreadType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class TransactionAmountStrategyTest {
  @InjectMocks private TransactionAmountStrategy strategy;

  @Test
  void shouldUseHighestMatchingThreshold() {
    Account account =
        Account.builder()
            .spread(
                Spread.builder()
                    .type(SpreadType.TRANSACTION_AMOUNT)
                    .build()
                    .addSpreadLevel(0, new BigDecimal("0.50")) // 0.50%
                    .addSpreadLevel(1000, new BigDecimal("1.00")) // 1.00%
                    .addSpreadLevel(5000, new BigDecimal("1.50"))) // 1.50%
            .build();
    BigDecimal amount = new BigDecimal("4999");

    BigDecimal result = strategy.calculateCommission(account, amount);

    // 4999 * 1.00% = 49.99 (skala 2, HALF_UP)
    assertEquals(new BigDecimal("49.99"), result);
  }

  @Test
  void shouldCalculateCommissionAtExactThreshold() {
    Account account =
        Account.builder()
            .spread(
                Spread.builder()
                    .type(SpreadType.TRANSACTION_AMOUNT)
                    .build()
                    .addSpreadLevel(0, new BigDecimal("0.50")) // 0.50%
                    .addSpreadLevel(5000, new BigDecimal("1.50"))) // 1.50%
            .build();
    BigDecimal amount = new BigDecimal("5000");

    BigDecimal result = strategy.calculateCommission(account, amount);

    // 5000 * 1.50% = 75.00
    assertEquals(new BigDecimal("75.00"), result);
  }

  @Test
  void shouldReturnZeroWhenNoThresholdMatches() {
    Account account =
        Account.builder()
            .spread(
                Spread.builder()
                    .type(SpreadType.TRANSACTION_AMOUNT)
                    .build()
                    .addSpreadLevel(1000, new BigDecimal("1.00"))
                    .addSpreadLevel(5000, new BigDecimal("1.50")))
            .build();
    BigDecimal amount = new BigDecimal("500");

    BigDecimal result = strategy.calculateCommission(account, amount);

    assertEquals(0, result.compareTo(BigDecimal.ZERO));
  }

  @Test
  void shouldRoundHalfUpToTwoDecimals() {
    Account account =
        Account.builder()
            .spread(
                Spread.builder()
                    .type(SpreadType.TRANSACTION_AMOUNT)
                    .build()
                    .addSpreadLevel(0, new BigDecimal("2.345"))) // 2.345%
            .build();
    BigDecimal amount = new BigDecimal("100");

    BigDecimal result = strategy.calculateCommission(account, amount);

    assertEquals(new BigDecimal("2.35"), result);
  }

  @Test
  void shouldReturnZeroWhenAmountIsZero() {
    Account account =
        Account.builder()
            .spread(
                Spread.builder()
                    .type(SpreadType.TRANSACTION_AMOUNT)
                    .build()
                    .addSpreadLevel(0, new BigDecimal("1.25")))
            .build();
    BigDecimal amount = BigDecimal.ZERO;

    BigDecimal result = strategy.calculateCommission(account, amount);

    assertEquals(0, result.compareTo(BigDecimal.ZERO));
  }
}
