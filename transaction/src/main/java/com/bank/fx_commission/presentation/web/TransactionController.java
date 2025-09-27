package com.bank.fx_commission.presentation.web;

import com.bank.fx_commission.application.ApproveTransactionUseCase;
import com.bank.fx_commission.application.InitiateTransactionUseCase;
import com.bank.fx_commission.application.dto.ApproveTransactionUseCaseDTO;
import com.bank.fx_commission.application.dto.InitiateTransactionUseCaseDTO;
import com.bank.fx_commission.domain.Transaction;
import com.bank.fx_commission.presentation.web.dto.InitiateTransactionDTO;
import com.bank.fx_commission.presentation.web.dto.TransactionResponseDTO;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {
    private final InitiateTransactionUseCase initiateTransactionUseCase;
    private final ApproveTransactionUseCase approveTransactionUseCase;

    TransactionController(InitiateTransactionUseCase initiateTransactionUseCase, ApproveTransactionUseCase approveTransactionUseCase) {
        this.initiateTransactionUseCase = initiateTransactionUseCase;
        this.approveTransactionUseCase = approveTransactionUseCase;
    }

    @PostMapping(path = "/initiate")
    public TransactionResponseDTO initiateTransaction(@RequestBody InitiateTransactionDTO dto) {
        Transaction transaction = this.initiateTransactionUseCase.execute(
                new InitiateTransactionUseCaseDTO(
                    UUID.randomUUID(),
                    dto.customerId(),
                    dto.sourceAccountIban(),
                    dto.destinationAccountIban(),
                    dto.amount(),
                    dto.title()
                )
        );

        return TransactionResponseDTO.fromTransaction(transaction);
    }

    @PostMapping(path = "/{id}/approve")
    public TransactionResponseDTO approveTransaction(@PathVariable("id") UUID id) {
        return TransactionResponseDTO.fromTransaction(
                this.approveTransactionUseCase.execute(new ApproveTransactionUseCaseDTO(id))
        );
    }
}
