package com.bank.fxcommission.application.dto;


import java.math.BigDecimal;
import java.util.UUID;

public record InitiateTransactionUseCaseDTO(
    UUID id,
    UUID customerId,
    String sourceAccountIban,
    String destinationAccountIban,
    BigDecimal amount,
    String title
) {
}
