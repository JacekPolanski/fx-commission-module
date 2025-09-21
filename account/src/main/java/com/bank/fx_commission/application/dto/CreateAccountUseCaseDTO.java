package com.bank.fx_commission.application.dto;

import java.util.UUID;

public record CreateAccountUseCaseDTO(UUID id, UUID customerId, String name, String currency, String number) {}