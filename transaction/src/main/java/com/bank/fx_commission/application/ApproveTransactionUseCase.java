package com.bank.fx_commission.application;

import com.bank.fx_commission.application.dto.ApproveTransactionUseCaseDTO;
import com.bank.fx_commission.domain.Transaction;
import com.bank.fx_commission.domain.TransactionRepository;
import com.bank.fx_commission.shared.UseCase;
import org.springframework.stereotype.Service;

@Service
public class ApproveTransactionUseCase implements UseCase<ApproveTransactionUseCaseDTO, Transaction> {
    private final TransactionRepository transactionRepository;

    public ApproveTransactionUseCase(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Transaction execute(ApproveTransactionUseCaseDTO dto) {
        Transaction transaction = this.transactionRepository.getReferenceById(dto.id());
        transaction.approve();
        this.transactionRepository.save(transaction);

        return transaction;
    }
}
