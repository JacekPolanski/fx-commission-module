package com.bank.fx_commission.application.dto;

import org.javamoney.moneta.Money;

import java.util.Currency;
import java.util.UUID;

public record InitiateTransactionUseCaseDTO(
    UUID id,
    UUID customerId,
    String sourceAccountIban,
    String destinationAccountIban,
    Money amount,
    String title
) {
    public Currency currency() {
        return Currency.getInstance(amount.getCurrency().getCurrencyCode());
    }
}
