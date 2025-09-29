package com.bank.fxcommission.application.service;

import com.bank.fxcommission.domain.Account;
import com.bank.fxcommission.domain.AccountRepository;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AccountBalanceUpdater {
  private final AccountRepository accountRepository;

  public void updateBaseAccount(UUID accountId, BigDecimal amount) throws IllegalArgumentException {
    Account sourceAccount = this.accountRepository.getReferenceById(accountId);
    sourceAccount.subtractFromTheBalance(amount);

    this.accountRepository.save(sourceAccount);
  }

  public void updateDestinationAccount(UUID accountId, BigDecimal amount) {
    Account destinationAccount = this.accountRepository.getReferenceById(accountId);
    destinationAccount.addToTheBalance(amount);

    this.accountRepository.save(destinationAccount);
  }
}
