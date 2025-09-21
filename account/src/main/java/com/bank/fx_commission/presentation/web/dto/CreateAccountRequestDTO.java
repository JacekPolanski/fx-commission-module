package com.bank.fx_commission.presentation.web.dto;

import java.util.UUID;

public record CreateAccountRequestDTO(UUID customerId, String name, String currency, String iban) {}