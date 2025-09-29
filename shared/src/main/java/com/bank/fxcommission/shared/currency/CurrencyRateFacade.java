package com.bank.fxcommission.shared.currency;

import java.math.BigDecimal;
import java.util.Currency;


public interface CurrencyRateFacade {
    BigDecimal calculateRate(Currency fromCurrency, Currency toCurrency);
    Currency getReferenceCurrency();
}
