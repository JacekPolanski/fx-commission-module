package com.bank.fx_commission.shared.account;

import java.math.BigDecimal;

public interface AccountFacade {
    Account findAccountByIban(String iban);
    BigDecimal calculateCommission(Account account, BigDecimal amount);
}
