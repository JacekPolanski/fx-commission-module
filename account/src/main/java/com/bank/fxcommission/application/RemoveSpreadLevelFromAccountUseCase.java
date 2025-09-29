package com.bank.fxcommission.application;

import com.bank.fxcommission.application.dto.RemoveSpreadLevelFromAccountUseCaseDto;
import com.bank.fxcommission.domain.Account;
import com.bank.fxcommission.domain.AccountRepository;
import com.bank.fxcommission.shared.UseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RemoveSpreadLevelFromAccountUseCase
    implements UseCase<RemoveSpreadLevelFromAccountUseCaseDto, Account> {
  @Autowired private AccountRepository accountRepository;

  @Override
  public Account execute(RemoveSpreadLevelFromAccountUseCaseDto dto) {
    final var account = accountRepository.getReferenceById(dto.id());
    account.getSpread().removeSpreadLevel(dto.threshold());
    return accountRepository.save(account);
  }
}
