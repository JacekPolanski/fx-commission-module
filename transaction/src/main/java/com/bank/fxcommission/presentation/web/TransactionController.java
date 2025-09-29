package com.bank.fxcommission.presentation.web;

import com.bank.fxcommission.application.ApproveTransactionUseCase;
import com.bank.fxcommission.application.InitiateTransactionUseCase;
import com.bank.fxcommission.application.dto.ApproveTransactionUseCaseDto;
import com.bank.fxcommission.application.dto.InitiateTransactionUseCaseDto;
import com.bank.fxcommission.domain.Transaction;
import com.bank.fxcommission.domain.TransactionRepository;
import com.bank.fxcommission.presentation.web.dto.InitiateTransactionDto;
import com.bank.fxcommission.presentation.web.dto.TransactionResponseDto;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transaction")
@RequiredArgsConstructor
public class TransactionController {
  private final InitiateTransactionUseCase initiateTransactionUseCase;
  private final ApproveTransactionUseCase approveTransactionUseCase;
  private final TransactionRepository transactionRepository;

  @PostMapping(path = "/initiate")
  public TransactionResponseDto initiateTransaction(@RequestBody InitiateTransactionDto dto) {
    final Transaction transaction =
        this.initiateTransactionUseCase.execute(
            new InitiateTransactionUseCaseDto(
                UUID.randomUUID(),
                dto.customerId(),
                dto.sourceAccountIban(),
                dto.destinationAccountIban(),
                dto.amount(),
                dto.title()));

    return TransactionResponseDto.fromTransaction(transaction);
  }

  @PostMapping(path = "/{id}/approve")
  public TransactionResponseDto approveTransaction(@PathVariable("id") UUID id) {
    return TransactionResponseDto.fromTransaction(
        this.approveTransactionUseCase.execute(new ApproveTransactionUseCaseDto(id)));
  }

  @GetMapping
  @ResponseBody
  public List<TransactionResponseDto> getAllTransactions() {
    return this.transactionRepository.findAll().stream()
        .map(TransactionResponseDto::fromTransaction)
        .toList();
  }
}
