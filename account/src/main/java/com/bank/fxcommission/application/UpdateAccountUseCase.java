package com.bank.fxcommission.application;

import com.bank.fxcommission.application.dto.UpdateAccountUseCaseDto;
import com.bank.fxcommission.domain.Account;
import com.bank.fxcommission.domain.AccountRepository;
import com.bank.fxcommission.shared.UseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateAccountUseCase implements UseCase<UpdateAccountUseCaseDto, Account> {
  @Autowired
  private AccountRepository accountRepository;

  @Override
  public Account execute(UpdateAccountUseCaseDto dto) {
    final Account account = this.accountRepository.getReferenceById(dto.id());
    account.setName(dto.name());
    account.setSpreadType(dto.spreadType());

    return this.accountRepository.save(account);
  }
}
