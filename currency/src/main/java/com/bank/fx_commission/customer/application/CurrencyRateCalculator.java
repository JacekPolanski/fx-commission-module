package com.bank.fx_commission.customer.application;

import com.bank.fx_commission.customer.domain.CurrencyRate;
import com.bank.fx_commission.customer.domain.CurrencyRateRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Currency;

@Service
public class CurrencyRateCalculator {
    public static final Currency BASE_CURRENCY = Currency.getInstance("EUR");
    private final CurrencyRateRepository repository;

    public CurrencyRateCalculator(CurrencyRateRepository repository) {
        this.repository = repository;
    }

    public BigDecimal calculateRate(Currency from, Currency to) {
        if (from.equals(to)) {
            return BigDecimal.ONE;
        }

        CurrencyRate sourceCurrency = repository.getReferenceById(to.getCurrencyCode());

        if (BASE_CURRENCY.equals(from)) {
            return sourceCurrency.getRate();
        }

        BigDecimal baseRate = this.calculateRate(BASE_CURRENCY, from);
        return sourceCurrency.getRate().multiply(baseRate);
    }
}
