package com.bank.fxcommission.application;

import com.bank.fxcommission.application.dto.CreateAccountUseCaseDto;
import com.bank.fxcommission.domain.Account;
import com.bank.fxcommission.domain.AccountRepository;
import com.bank.fxcommission.shared.UseCase;
import com.bank.fxcommission.shared.customer.CustomerFacade;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateAccountUseCase implements UseCase<CreateAccountUseCaseDto, Account> {
  private final CustomerFacade customerFacade;
  private final AccountRepository repository;

  @Override
  public Account execute(CreateAccountUseCaseDto dto) {
    if (!customerFacade.isCustomerActive(dto.customerId())) {
      throw new IllegalArgumentException("Customer with id " + dto.customerId() + " not found");
    }

    final Account account =
        Account.builder()
            .id(dto.id())
            .customerId(dto.customerId())
            .name(dto.name())
            .currency(dto.currency())
            .balance(BigDecimal.ZERO)
            .iban(dto.iban())
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .active(true)
            .build();

    repository.save(account);

    return account;
  }
}
