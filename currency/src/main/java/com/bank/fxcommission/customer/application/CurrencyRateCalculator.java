package com.bank.fxcommission.customer.application;

import com.bank.fxcommission.customer.domain.CurrencyRateRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrencyRateCalculator {
  public static final Currency BASE_CURRENCY = Currency.getInstance("EUR");
  private final CurrencyRateRepository repository;

  public BigDecimal calculateRate(Currency from, Currency to) {
    if (from.equals(to)) {
      return BigDecimal.ONE;
    }

    final BigDecimal rate;

    if (from.equals(BASE_CURRENCY)) {
      rate = repository.getReferenceById(to.getCurrencyCode()).getRate();
    } else if (to.equals(BASE_CURRENCY)) {
      rate =
          BigDecimal.ONE.divide(
              repository.getReferenceById(from.getCurrencyCode()).getRate(),
              10,
              RoundingMode.HALF_UP);
    } else {
      final BigDecimal fromRate = repository.getReferenceById(from.getCurrencyCode()).getRate();
      final BigDecimal toRate = repository.getReferenceById(to.getCurrencyCode()).getRate();
      rate = toRate.divide(fromRate, 10, RoundingMode.HALF_UP);
    }

    return BigDecimal.ONE.divide(rate, 10, RoundingMode.HALF_UP);
  }
}
