package com.bank.fx_commission.shared;

import com.bank.fx_commission.application.commsion.CommissionCalculator;
import com.bank.fx_commission.domain.Account;
import com.bank.fx_commission.domain.AccountRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class AccountFacade implements com.bank.fx_commission.shared.account.AccountFacade {
    private final AccountRepository accountRepository;

    AccountFacade(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account findAccountByIban(String iban) {
        return accountRepository.findByIban(iban);
    }

    @Override
    public BigDecimal calculateCommission(UUID accountId, BigDecimal amount) {
        Account account = accountRepository.findById(accountId).orElseThrow();
        CommissionCalculator calculator = new CommissionCalculator(account.getSpreadStrategy());

        return calculator.calculateCommission(account, amount);
    }
}
