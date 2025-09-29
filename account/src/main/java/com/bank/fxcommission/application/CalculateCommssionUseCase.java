package com.bank.fxcommission.application;

import com.bank.fxcommission.application.dto.CalculateCommissionUseCaseDto;
import com.bank.fxcommission.application.service.CommissionCalculator;
import com.bank.fxcommission.domain.Account;
import com.bank.fxcommission.domain.AccountRepository;
import com.bank.fxcommission.domain.strategies.CommissionStrategy;
import com.bank.fxcommission.domain.strategies.FixedStrategy;
import com.bank.fxcommission.domain.strategies.NoCommissionStrategy;
import com.bank.fxcommission.domain.strategies.TransactionAmountStrategy;
import com.bank.fxcommission.shared.UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CalculateCommssionUseCase
    implements UseCase<CalculateCommissionUseCaseDto, BigDecimal> {
  private final AccountRepository accountRepository;

  @Override
  public BigDecimal execute(CalculateCommissionUseCaseDto dto) {
    final Account account = accountRepository.findById(dto.accountId()).orElseThrow();
    final CommissionStrategy strategy;
    switch (account.getSpread().getType()) {
      case FIXED -> strategy = new FixedStrategy();
      case PERCENTAGE_OF_TRANSACTIONS_COUNT_MONTHLY ->
          strategy = new NoCommissionStrategy(); // ToDo: implement
      case TRANSACTION_AMOUNT -> strategy = new TransactionAmountStrategy();
      default -> strategy = new NoCommissionStrategy();
    }

    final CommissionCalculator calculator = new CommissionCalculator(strategy);

    return calculator.calculateCommission(account, dto.amount());
  }
}
