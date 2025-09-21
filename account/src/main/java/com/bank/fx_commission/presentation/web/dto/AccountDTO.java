package com.bank.fx_commission.presentation.web.dto;

import java.util.UUID;

public record AccountDTO(UUID id, String name, String iban, String currency, String balance) {}
