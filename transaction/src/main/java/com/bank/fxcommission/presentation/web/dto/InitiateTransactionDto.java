package com.bank.fxcommission.presentation.web.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record InitiateTransactionDto(
    UUID customerId,
    String sourceAccountIban,
    String destinationAccountIban,
    BigDecimal amount,
    String title) {}
