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
public class FixedStrategyTest {
  @InjectMocks private FixedStrategy strategy;

  @Test
  void shouldCalculateCommission() {
    Account account =
        Account.builder()
            .spread(
                Spread.builder()
                    .type(SpreadType.FIXED)
                    .build()
                    .addSpreadLevel(0, BigDecimal.valueOf(0.05)))
            .build();
    BigDecimal amount = BigDecimal.valueOf(100);

    BigDecimal result = strategy.calculateCommission(account, amount);

    assertEquals(BigDecimal.valueOf(0.05), result);
  }

  @Test
  void shouldUseFirstSpreadLevelWhenMultiplePresent() {
    Account account =
        Account.builder()
            .spread(
                Spread.builder()
                    .type(SpreadType.FIXED)
                    .build()
                    .addSpreadLevel(0, BigDecimal.valueOf(0.05))
                    .addSpreadLevel(1_000, BigDecimal.valueOf(0.10))
                    .addSpreadLevel(10_000, BigDecimal.valueOf(0.20)))
            .build();
    BigDecimal amount = BigDecimal.valueOf(10_000);

    BigDecimal result = strategy.calculateCommission(account, amount);

    assertEquals(BigDecimal.valueOf(5.00).stripTrailingZeros(), result.stripTrailingZeros());
  }

  @Test
  void shouldReturnZeroWhenAmountIsZero() {
    Account account =
        Account.builder()
            .spread(
                Spread.builder()
                    .type(SpreadType.FIXED)
                    .build()
                    .addSpreadLevel(0, BigDecimal.valueOf(0.25)))
            .build();
    BigDecimal amount = BigDecimal.ZERO;

    BigDecimal result = strategy.calculateCommission(account, amount);

    assertEquals(0, result.compareTo(BigDecimal.ZERO));
  }

  @Test
  void shouldReturnZeroWhenSpreadIsZero() {
    Account account =
        Account.builder()
            .spread(
                Spread.builder().type(SpreadType.FIXED).build().addSpreadLevel(0, BigDecimal.ZERO))
            .build();
    BigDecimal amount = BigDecimal.valueOf(1234.56);

    BigDecimal result = strategy.calculateCommission(account, amount);

    assertEquals(0, result.compareTo(BigDecimal.ZERO));
  }

  @Test
  void shouldCalculateCommissionForLargeAmount() {
    Account account =
        Account.builder()
            .spread(
                Spread.builder()
                    .type(SpreadType.FIXED)
                    .build()
                    .addSpreadLevel(0, BigDecimal.valueOf(1.25)))
            .build();
    BigDecimal amount = new BigDecimal("1000000000"); // 1e9

    BigDecimal result = strategy.calculateCommission(account, amount);

    // 1_000_000_000 * 1.25 / 100 = 12_500_000
    assertEquals(0, result.compareTo(new BigDecimal("12500000")));
  }
}
