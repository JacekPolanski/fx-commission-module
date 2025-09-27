package com.bank.fx_commission.shared;

import com.bank.fx_commission.application.service.AccountBalanceUpdater;
import com.bank.fx_commission.application.service.CommissionCalculator;
import com.bank.fx_commission.domain.Account;
import com.bank.fx_commission.domain.AccountRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class AccountFacade implements com.bank.fx_commission.shared.account.AccountFacade {
    private final AccountRepository accountRepository;
    private final AccountBalanceUpdater updater;

    AccountFacade(AccountRepository accountRepository, AccountBalanceUpdater updater) {
        this.accountRepository = accountRepository;
        this.updater = updater;
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

    @Override
    public void updateBaseAccountBalance(UUID accountId, BigDecimal amount) throws IllegalArgumentException {
        this.updater.updateBaseAccount(accountId, amount);
    }

    @Override
    public void updateDestinationAccountBalance(UUID accountId, BigDecimal amount) throws IllegalArgumentException {
        this.updater.updateDestinationAccount(accountId, amount);
    }
}
