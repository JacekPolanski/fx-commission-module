package com.bank.fx_commission.application;

import com.bank.fx_commission.application.dto.InitiateTransactionUseCaseDTO;
import com.bank.fx_commission.domain.Transaction;
import com.bank.fx_commission.domain.TransactionRepository;
import com.bank.fx_commission.shared.UseCase;
import com.bank.fx_commission.shared.account.Account;
import com.bank.fx_commission.shared.account.AccountFacade;
import com.bank.fx_commission.shared.customer.CustomerFacade;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class InitiateTransactionUseCase implements UseCase<InitiateTransactionUseCaseDTO, Transaction> {
    private final TransactionRepository transactionRepository;
    private final AccountFacade accountFacade;
    private final CustomerFacade customerFacade;

    InitiateTransactionUseCase(
            TransactionRepository transactionRepository,
            AccountFacade accountFacade,
            CustomerFacade customerFacade
    ) {
        this.transactionRepository = transactionRepository;
        this.accountFacade = accountFacade;
        this.customerFacade = customerFacade;
    }

    @Override
    public Transaction execute(InitiateTransactionUseCaseDTO dto) {
        if (!customerFacade.isCustomerActive(dto.customerId())) {
            throw new IllegalArgumentException("Customer with id " + dto.customerId() + " not found");
        }

        Account sourceAccount = accountFacade.findAccountByIban(dto.sourceAccountIban());
        if (!sourceAccount.isBelongsToCustomer(dto.customerId())) {
            throw new IllegalArgumentException("Account with iban " + dto.sourceAccountIban() + " not belongs to customer with id " + dto.customerId());
        }
        if (!sourceAccount.supportsCurrency(dto.currency())) {
            throw new IllegalArgumentException("Account with iban " + dto.sourceAccountIban() + " does not support currency " + dto.currency());
        }

        Account destinationAccount = accountFacade.findAccountByIban(dto.destinationAccountIban());
        if (!destinationAccount.isActive()) {
            throw new IllegalArgumentException("Account with iban " + dto.destinationAccountIban() + " not active");
        }

        Transaction transaction = Transaction.builder()
                .id(dto.id())
                .sourceAccountId(sourceAccount.id())
                .destinationAccountId(destinationAccount.id())
                .sourceCurrency(sourceAccount.getCurrency())
                .destinationCurrency(destinationAccount.getCurrency())
                .amount(dto.amount().getNumber().numberValue(BigDecimal.class))
                .title(dto.title())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        transactionRepository.save(transaction);

        return transaction;
    }
}
