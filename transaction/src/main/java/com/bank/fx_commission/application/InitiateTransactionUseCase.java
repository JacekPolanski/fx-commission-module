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

        Account quoteAccount = accountFacade.findAccountByIban(dto.destinationAccountIban());
        if (!quoteAccount.isActive()) {
            throw new IllegalArgumentException("Account with iban " + dto.destinationAccountIban() + " not active");
        }

        BigDecimal baseToReferenceRate = this.currencyRateFacade.calculateRate(
                sourceAccount.getCurrency(),
                this.currencyRateFacade.getReferenceCurrency()
        ).setScale(10, RoundingMode.HALF_UP);

        BigDecimal baseToQuoteRate;
        if (sourceAccount.getCurrency().equals(quoteAccount.getCurrency())) {
            baseToQuoteRate = BigDecimal.ONE.setScale(10, RoundingMode.HALF_UP);
        } else {
            baseToQuoteRate = this.currencyRateFacade.calculateRate(
                    sourceAccount.getCurrency(),
                    quoteAccount.getCurrency()
            ).setScale(10, RoundingMode.HALF_UP);
        }

        BigDecimal commission = accountFacade.calculateCommission(sourceAccount.id(), dto.amount());

        Transaction transaction = Transaction.builder()
                .id(dto.id())
                .sourceAccountId(sourceAccount.id())
                .destinationAccountId(quoteAccount.id())
                .baseCurrency(sourceAccount.getCurrency())
                .quoteCurrency(quoteAccount.getCurrency())
                .referenceCurrency(this.currencyRateFacade.getReferenceCurrency())
                .amountInBaseCurrency(dto.amount())
                .baseToQuoteRate(baseToQuoteRate)
                .commission(commission)
                .baseToReferenceRate(baseToReferenceRate)
                .title(dto.title())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build()
                .calculateAmountInQuoteCurrency()
                .calculateAmountInReferenceCurrency();

        transactionRepository.save(transaction);

        return transaction;
    }
}
