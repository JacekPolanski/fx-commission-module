package com.bank.fxcommission.application;

import com.bank.fxcommission.application.dto.UpdateSpreadLevelInAccountUseCaseDto;
import com.bank.fxcommission.domain.Account;
import com.bank.fxcommission.domain.AccountRepository;
import com.bank.fxcommission.shared.UseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateSpreadLevelInAccountUseCase
    implements UseCase<UpdateSpreadLevelInAccountUseCaseDto, Account> {
  @Autowired private AccountRepository accountRepository;

  @Override
  public Account execute(UpdateSpreadLevelInAccountUseCaseDto dto) {
    final var account = accountRepository.getReferenceById(dto.id());
    account.getSpread().updateSpreadLevel(dto.threshold(), dto.commission());
    return accountRepository.save(account);
  }
}
