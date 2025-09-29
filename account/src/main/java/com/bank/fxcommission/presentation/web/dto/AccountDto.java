package com.bank.fxcommission.presentation.web.dto;

import com.bank.fxcommission.domain.Account;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

public record AccountDto(
    UUID id,
    String name,
    String iban,
    String currency,
    String balance,
    Map<Integer, BigDecimal> levels,
    String spreadType) {

  public static AccountDto fromAccount(Account account) {
    return new AccountDto(
        account.getId(),
        account.getName(),
        account.getIban(),
        account.getCurrency().getCurrencyCode(),
        account.getBalance().toString(),
        account.getSpread().getSpreadLevels(),
        account.getSpread().getType().toString());
  }
}
