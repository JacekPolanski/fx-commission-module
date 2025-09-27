package com.bank.fx_commission.customer.application;

import com.bank.fx_commission.customer.domain.CurrencyRateRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

        BigDecimal rate;

        if (from.equals(BASE_CURRENCY)) {
            rate = repository.getReferenceById(to.getCurrencyCode()).getRate();
        } else if (to.equals(BASE_CURRENCY)) {
            rate = BigDecimal.ONE.divide(
                    repository.getReferenceById(from.getCurrencyCode()).getRate(),
                    10,
                    RoundingMode.HALF_UP
            );
        } else {
            BigDecimal fromRate = repository.getReferenceById(from.getCurrencyCode()).getRate();
            BigDecimal toRate = repository.getReferenceById(to.getCurrencyCode()).getRate();
            rate = toRate.divide(fromRate, 10, RoundingMode.HALF_UP);
        }

        return BigDecimal.ONE.divide(rate, 10, RoundingMode.HALF_UP);
    }
}
