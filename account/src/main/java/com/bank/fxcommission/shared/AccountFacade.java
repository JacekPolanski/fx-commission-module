package com.bank.fxcommission.shared;

import com.bank.fxcommission.application.service.AccountBalanceUpdater;
import com.bank.fxcommission.application.service.CommissionCalculator;
import com.bank.fxcommission.domain.Account;
import com.bank.fxcommission.domain.AccountRepository;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AccountFacade implements com.bank.fxcommission.shared.account.AccountFacade {
  private final AccountRepository accountRepository;
  private final AccountBalanceUpdater updater;

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
  public void updateBaseAccountBalance(UUID accountId, BigDecimal amount)
      throws IllegalArgumentException {
    this.updater.updateBaseAccount(accountId, amount);
  }

  @Override
  public void updateDestinationAccountBalance(UUID accountId, BigDecimal amount)
      throws IllegalArgumentException {
    this.updater.updateDestinationAccount(accountId, amount);
  }
}
