package com.bank.fx_commission.application;

import com.bank.fx_commission.application.dto.InitiateTransactionUseCaseDTO;
import com.bank.fx_commission.domain.Transaction;
import com.bank.fx_commission.domain.TransactionRepository;
import com.bank.fx_commission.shared.UseCase;
import com.bank.fx_commission.shared.account.Account;
import com.bank.fx_commission.shared.account.AccountFacade;
import com.bank.fx_commission.shared.currency.CurrencyRateFacade;
import com.bank.fx_commission.shared.customer.CustomerFacade;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Service
public class InitiateTransactionUseCase implements UseCase<InitiateTransactionUseCaseDTO, Transaction> {
    private final TransactionRepository transactionRepository;
    private final AccountFacade accountFacade;
    private final CustomerFacade customerFacade;
    private final CurrencyRateFacade currencyRateFacade;

    InitiateTransactionUseCase(
            TransactionRepository transactionRepository,
            AccountFacade accountFacade,
            CustomerFacade customerFacade,
            CurrencyRateFacade currencyRateFacade
    ) {
        this.transactionRepository = transactionRepository;
        this.accountFacade = accountFacade;
        this.customerFacade = customerFacade;
        this.currencyRateFacade = currencyRateFacade;
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

        Account destinationAccount = accountFacade.findAccountByIban(dto.destinationAccountIban());
        if (!destinationAccount.isActive()) {
            throw new IllegalArgumentException("Account with iban " + dto.destinationAccountIban() + " not active");
        }

        BigDecimal referenceRate = this.currencyRateFacade.calculateRate(
                sourceAccount.getCurrency(),
                this.currencyRateFacade.getReferenceCurrency()
        );

        BigDecimal rate = BigDecimal.ONE;
        if (!sourceAccount.getCurrency().equals(destinationAccount.getCurrency())) {
            BigDecimal destinationRate = this.currencyRateFacade.calculateRate(
                    this.currencyRateFacade.getReferenceCurrency(),
                    destinationAccount.getCurrency()
            );

            rate = referenceRate.multiply(destinationRate).setScale(10, RoundingMode.HALF_UP);
        } else if (destinationAccount.getCurrency().equals(this.currencyRateFacade.getReferenceCurrency())) {
            rate = referenceRate;
        }

        BigDecimal commission = accountFacade.calculateCommission(sourceAccount, dto.amount());

        Transaction transaction = Transaction.builder()
                .id(dto.id())
                .sourceAccountId(sourceAccount.id())
                .destinationAccountId(destinationAccount.id())
                .sourceCurrency(sourceAccount.getCurrency())
                .destinationCurrency(destinationAccount.getCurrency())
                .referenceCurrency(this.currencyRateFacade.getReferenceCurrency())
                .amountInSourceCurrency(dto.amount())
                .rate(rate)
                .commission(commission)
                .referenceRate(referenceRate)
                .title(dto.title())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build()
                .calculateAmountInDestinationCurrency()
                .calculateAmountInReferenceCurrency();

        transactionRepository.save(transaction);

        return transaction;
    }
}
