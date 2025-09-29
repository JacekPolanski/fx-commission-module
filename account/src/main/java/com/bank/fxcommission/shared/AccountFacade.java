package com.bank.fxcommission.shared;

import com.bank.fxcommission.application.CalculateCommssionUseCase;
import com.bank.fxcommission.application.service.AccountBalanceUpdater;
import com.bank.fxcommission.application.service.CommissionCalculator;
import com.bank.fxcommission.domain.Account;
import com.bank.fxcommission.domain.AccountRepository;
import java.math.BigDecimal;
import java.util.UUID;

import com.bank.fxcommission.domain.strategies.CommissionStrategy;
import com.bank.fxcommission.domain.strategies.FixedStrategy;
import com.bank.fxcommission.domain.strategies.NoCommissionStrategy;
import com.bank.fxcommission.domain.strategies.TransactionAmountStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountFacade implements com.bank.fxcommission.shared.account.AccountFacade {
  private final AccountRepository accountRepository;
  private final AccountBalanceUpdater updater;
  private final CalculateCommssionUseCase calculator;

  @Override
  public Account findAccountByIban(String iban) {
    return accountRepository.findByIban(iban);
  }

  @Override
  public BigDecimal calculateCommission(UUID accountId, BigDecimal amount) {
    final Account account = accountRepository.findById(accountId).orElseThrow();
    final CommissionStrategy strategy;
    switch (account.getSpread().getType()) {
      case FIXED -> strategy = new FixedStrategy();
      case PERCENTAGE_OF_TRANSACTIONS_COUNT_MONTHLY ->
          strategy = new NoCommissionStrategy(); // ToDo: implement
      case TRANSACTION_AMOUNT -> strategy = new TransactionAmountStrategy(); // ToDo: implement
      default -> strategy = new NoCommissionStrategy();
    }

    final CommissionCalculator calculator = new CommissionCalculator(strategy);

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
