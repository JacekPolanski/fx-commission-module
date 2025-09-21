package com.bank.fx_commission.presentation.web;

import com.bank.fx_commission.application.InitiateTransactionUseCase;
import com.bank.fx_commission.application.dto.InitiateTransactionUseCaseDTO;
import com.bank.fx_commission.domain.Transaction;
import com.bank.fx_commission.presentation.web.dto.InitiateTransactionDTO;
import org.javamoney.moneta.Money;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {
    private final InitiateTransactionUseCase initiateTransactionUseCase;

    TransactionController(InitiateTransactionUseCase initiateTransactionUseCase) {
        this.initiateTransactionUseCase = initiateTransactionUseCase;
    }

    @PostMapping(path = "/initiate")
    public Transaction initiateTransaction(@RequestBody InitiateTransactionDTO dto) {
        return initiateTransactionUseCase.execute(
                new InitiateTransactionUseCaseDTO(
                    UUID.randomUUID(),
                    dto.customerId(),
                    dto.sourceAccountIban(),
                    dto.destinationAccountIban(),
                    Money.of(dto.amount(), dto.currency()),
                    dto.title()
                )
        );
    }
}
