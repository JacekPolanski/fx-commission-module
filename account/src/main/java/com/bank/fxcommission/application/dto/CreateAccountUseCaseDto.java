package com.bank.fxcommission.application.dto;

import java.util.Currency;
import java.util.UUID;

public record CreateAccountUseCaseDto(
    UUID id, UUID customerId, String name, Currency currency, String iban) {}
