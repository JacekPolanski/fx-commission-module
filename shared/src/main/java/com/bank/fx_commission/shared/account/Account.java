package com.bank.fx_commission.shared.account;

import java.util.Currency;
import java.util.UUID;

public interface Account {
    boolean isActive();
    boolean isBelongsToCustomer(UUID customerId);
    UUID id();
    Currency getCurrency();
}
