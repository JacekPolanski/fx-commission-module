package com.bank.fxcommission.presentation.web;

import com.bank.fxcommission.application.ApproveTransactionUseCase;
import com.bank.fxcommission.application.InitiateTransactionUseCase;
import com.bank.fxcommission.application.dto.ApproveTransactionUseCaseDTO;
import com.bank.fxcommission.application.dto.InitiateTransactionUseCaseDTO;
import com.bank.fxcommission.domain.Transaction;
import com.bank.fxcommission.presentation.web.dto.InitiateTransactionDTO;
import com.bank.fxcommission.presentation.web.dto.TransactionResponseDTO;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transaction")
@RequiredArgsConstructor
public class TransactionController {
  private final InitiateTransactionUseCase initiateTransactionUseCase;
  private final ApproveTransactionUseCase approveTransactionUseCase;

  @PostMapping(path = "/initiate")
  public TransactionResponseDTO initiateTransaction(@RequestBody InitiateTransactionDTO dto) {
    Transaction transaction =
        this.initiateTransactionUseCase.execute(
            new InitiateTransactionUseCaseDTO(
                UUID.randomUUID(),
                dto.customerId(),
                dto.sourceAccountIban(),
                dto.destinationAccountIban(),
                dto.amount(),
                dto.title()));

    return TransactionResponseDTO.fromTransaction(transaction);
  }

  @PostMapping(path = "/{id}/approve")
  public TransactionResponseDTO approveTransaction(@PathVariable("id") UUID id) {
    return TransactionResponseDTO.fromTransaction(
        this.approveTransactionUseCase.execute(new ApproveTransactionUseCaseDTO(id)));
  }
}
