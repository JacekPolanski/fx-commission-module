package com.bank.fxcommission.shared.account;

import java.math.BigDecimal;
import java.util.UUID;

public interface AccountFacade {
    Account findAccountByIban(String iban);
    BigDecimal calculateCommission(UUID accountId, BigDecimal amount);
    void updateBaseAccountBalance(UUID accountId, BigDecimal amount) throws IllegalArgumentException;
    void updateDestinationAccountBalance(UUID accountId, BigDecimal amount) throws IllegalArgumentException;
}
