package com.bank.fx_commission.application.service;

import com.bank.fx_commission.domain.Transaction;
import com.bank.fx_commission.shared.currency.CurrencyRateFacade;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TransactionCommissionCalculator {
    private final CurrencyRateFacade currencyRateFacade;

    public TransactionCommissionCalculator(CurrencyRateFacade currencyRateFacade) {
        this.currencyRateFacade = currencyRateFacade;
    }

    public Transaction calculate(Transaction transaction) {
        BigDecimal rate = this.currencyRateFacade.calculateRate(transaction.getSourceCurrency(), transaction.getDestinationCurrency());
        transaction.setRate(rate);
        transaction.setAmountInDestinationCurrency(transaction.getAmount().multiply(rate));
        transaction.setCommission(BigDecimal.ZERO); //ToDo: calculate commission
        return transaction;
    }
}
