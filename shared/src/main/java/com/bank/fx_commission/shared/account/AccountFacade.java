package com.bank.fx_commission.shared.account;

import java.math.BigDecimal;
import java.util.UUID;

public interface AccountFacade {
    Account findAccountByIban(String iban);
    BigDecimal calculateCommission(UUID accountId, BigDecimal amount);
}
