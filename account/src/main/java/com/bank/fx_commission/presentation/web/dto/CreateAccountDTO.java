package com.bank.fx_commission.presentation.web.dto;

import java.util.UUID;

public record CreateAccountDTO(UUID customerId, String name, String currency, String number) {}