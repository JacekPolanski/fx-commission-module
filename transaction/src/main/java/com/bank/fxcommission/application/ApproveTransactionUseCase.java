package com.bank.fxcommission.application;

import com.bank.fxcommission.application.dto.ApproveTransactionUseCaseDTO;
import com.bank.fxcommission.domain.Transaction;
import com.bank.fxcommission.domain.TransactionRepository;
import com.bank.fxcommission.shared.UseCase;
import com.bank.fxcommission.shared.account.AccountFacade;
import com.bank.fxcommission.shared.event.TransactionApprovedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ApproveTransactionUseCase implements UseCase<ApproveTransactionUseCaseDTO, Transaction> {
    private final TransactionRepository transactionRepository;
    private final AccountFacade accountFacade;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public Transaction execute(ApproveTransactionUseCaseDTO dto) {
        Transaction transaction = this.transactionRepository.getReferenceById(dto.id());
        transaction.approve();
        this.transactionRepository.save(transaction);

        this.eventPublisher.publishEvent(new TransactionApprovedEvent(transaction));

        this.accountFacade.updateBaseAccountBalance(transaction.getSourceAccountId(), transaction.getAmountInBaseCurrency());
        this.accountFacade.updateDestinationAccountBalance(transaction.getDestinationAccountId(), transaction.getAmountInQuoteCurrency());

        return transaction;
    }
}
