package com.bank.fxcommission.presentation.web;

import com.bank.fxcommission.application.CreateAccountUseCase;
import com.bank.fxcommission.application.dto.CreateAccountUseCaseDto;
import com.bank.fxcommission.domain.Account;
import com.bank.fxcommission.domain.AccountRepository;
import com.bank.fxcommission.presentation.web.dto.AccountDto;
import com.bank.fxcommission.presentation.web.dto.CreateAccountRequestDto;
import java.util.Currency;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account")
public class AccountController {
  private final CreateAccountUseCase createAccountUseCase;
  private final AccountRepository accountRepository;

  @GetMapping
  @ResponseBody
  public List<AccountDto> getAllAccounts() {
    return this.accountRepository.findAllByActiveTrue().stream()
        .map(
            a ->
                new AccountDto(
                    a.getId(),
                    a.getName(),
                    a.getIban(),
                    a.getCurrency().getCurrencyCode(),
                    a.getBalance().toString(),
                    a.getSpread().getSpreadLevels()))
        .toList();
  }

  @PostMapping
  @ResponseBody
  public Account createAccount(@RequestBody CreateAccountRequestDto dto) {
    return createAccountUseCase.execute(
        new CreateAccountUseCaseDto(
            UUID.randomUUID(),
            dto.customerId(),
            dto.name(),
            Currency.getInstance(dto.currency()),
            dto.iban()));
  }
}
