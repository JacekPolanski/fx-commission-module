package com.bank.fx_commission.shared.account;

public interface AccountFacade {
    Account findAccountByIban(String iban);
}
