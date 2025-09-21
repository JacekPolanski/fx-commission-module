package com.bank.fx_commission.application;

import com.bank.fx_commission.application.dto.CreateAccountUseCaseDTO;
import com.bank.fx_commission.domain.Account;
import com.bank.fx_commission.domain.AccountRepository;
import com.bank.fx_commission.shared.UseCase;
import com.bank.fx_commission.shared.customer.CustomerFacadeInterface;
import com.bank.fx_commission.shared.customer.CustomerInterface;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CreateAccountUseCase implements UseCase<CreateAccountUseCaseDTO, Account> {
    private final CustomerFacadeInterface customerFacade;
    private final AccountRepository repository;

    CreateAccountUseCase(CustomerFacadeInterface facade, AccountRepository repository) {
        this.customerFacade = facade;
        this.repository = repository;
    }

    @Override
    public Account execute(CreateAccountUseCaseDTO dto) {
        CustomerInterface customer = customerFacade.getCustomerById(dto.customerId());

        Account account = Account.builder()
                .id(dto.id())
                .customerId(customer.getId())
                .name(dto.name())
                .currency(dto.currency())
                .number(dto.number())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        repository.save(account);

        return account;
    }
}
