package com.bank.fxcommission.application;

import com.bank.fxcommission.application.dto.AddSpreadLevelToAccountUseCaseDto;
import com.bank.fxcommission.domain.Account;
import com.bank.fxcommission.domain.AccountRepository;
import com.bank.fxcommission.shared.UseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddSpreadLevelToAccountUseCase
    implements UseCase<AddSpreadLevelToAccountUseCaseDto, Account> {
  @Autowired private AccountRepository accountRepository;

  @Override
  public Account execute(AddSpreadLevelToAccountUseCaseDto dto) {
    final var account = accountRepository.getReferenceById(dto.id());
    account.getSpread().addSpreadLevel(dto.threshold(), dto.commission());
    return accountRepository.save(account);
  }
}
