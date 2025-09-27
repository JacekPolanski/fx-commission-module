package com.bank.fx_commission.application;

import com.bank.fx_commission.application.dto.CreateAccountUseCaseDTO;
import com.bank.fx_commission.domain.Account;
import com.bank.fx_commission.domain.AccountRepository;
import com.bank.fx_commission.shared.UseCase;
import com.bank.fx_commission.shared.customer.CustomerFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CreateAccountUseCase implements UseCase<CreateAccountUseCaseDTO, Account> {
    private final CustomerFacade customerFacade;
    private final AccountRepository repository;

    @Override
    public Account execute(CreateAccountUseCaseDTO dto) {
        if (!customerFacade.isCustomerActive(dto.customerId())) {
            throw new IllegalArgumentException("Customer with id " + dto.customerId() + " not found");
        }

        Account account = Account.builder()
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
