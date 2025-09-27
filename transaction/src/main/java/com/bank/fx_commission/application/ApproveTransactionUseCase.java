package com.bank.fx_commission.application;

import com.bank.fx_commission.application.dto.ApproveTransactionUseCaseDTO;
import com.bank.fx_commission.domain.Transaction;
import com.bank.fx_commission.domain.TransactionRepository;
import com.bank.fx_commission.shared.UseCase;
import com.bank.fx_commission.shared.account.AccountFacade;
import org.springframework.stereotype.Service;

@Service
public class ApproveTransactionUseCase implements UseCase<ApproveTransactionUseCaseDTO, Transaction> {
    private final TransactionRepository transactionRepository;
    private final AccountFacade accountFacade;

    public ApproveTransactionUseCase(TransactionRepository transactionRepository, AccountFacade facade) {
        this.transactionRepository = transactionRepository;
        this.accountFacade = facade;
    }

    @Override
    public Transaction execute(ApproveTransactionUseCaseDTO dto) {
        Transaction transaction = this.transactionRepository.getReferenceById(dto.id());
        transaction.approve();
        this.transactionRepository.save(transaction);

        this.accountFacade.updateBaseAccountBalance(transaction.getSourceAccountId(), transaction.getAmountInBaseCurrency());
        this.accountFacade.updateDestinationAccountBalance(transaction.getDestinationAccountId(), transaction.getAmountInQuoteCurrency());

        return transaction;
    }
}
