package com.bank.fxcommission.customer.shared;

import com.bank.fxcommission.customer.application.CurrencyRateCalculator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Currency;

@Service
@RequiredArgsConstructor
public class CurrencyRateFacade implements com.bank.fxcommission.shared.currency.CurrencyRateFacade {
    private final CurrencyRateCalculator calculator;

    @Override
    public BigDecimal calculateRate(Currency fromCurrency, Currency toCurrency) {
        return calculator.calculateRate(fromCurrency, toCurrency);
    }

    @Override
    public Currency getReferenceCurrency() {
        return CurrencyRateCalculator.BASE_CURRENCY;
    }
}
