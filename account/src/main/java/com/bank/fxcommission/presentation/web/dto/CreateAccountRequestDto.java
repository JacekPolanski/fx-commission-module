package com.bank.fxcommission.presentation.web.dto;

import java.util.UUID;

public record CreateAccountRequestDto(UUID customerId, String name, String currency, String iban) {}
