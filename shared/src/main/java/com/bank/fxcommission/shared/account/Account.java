package com.bank.fxcommission.shared.account;

import java.util.Currency;
import java.util.UUID;

public interface Account {
    boolean isActive();
    boolean isBelongsToCustomer(UUID customerId);
    UUID id();
    Currency getCurrency();
}
