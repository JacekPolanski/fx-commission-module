package com.bank.fxcommission.presentation.web;

import com.bank.fxcommission.application.AddSpreadLevelToAccountUseCase;
import com.bank.fxcommission.application.CreateAccountUseCase;
import com.bank.fxcommission.application.RemoveSpreadLevelFromAccountUseCase;
import com.bank.fxcommission.application.UpdateSpreadLevelInAccountUseCase;
import com.bank.fxcommission.application.dto.AddSpreadLevelToAccountUseCaseDto;
import com.bank.fxcommission.application.dto.CreateAccountUseCaseDto;
import com.bank.fxcommission.application.dto.RemoveSpreadLevelFromAccountUseCaseDto;
import com.bank.fxcommission.application.dto.UpdateSpreadLevelInAccountUseCaseDto;
import com.bank.fxcommission.domain.AccountRepository;
import com.bank.fxcommission.presentation.web.dto.AccountDto;
import com.bank.fxcommission.presentation.web.dto.SpreadLevelDto;
import com.bank.fxcommission.presentation.web.dto.CreateAccountRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Currency;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account")
public class AccountController {
  private final CreateAccountUseCase createAccountUseCase;
  private final AccountRepository accountRepository;
  private final AddSpreadLevelToAccountUseCase addSpreadLevelToAccountUseCase;
  private final UpdateSpreadLevelInAccountUseCase updateSpreadLevelInAccountUseCase;
  private final RemoveSpreadLevelFromAccountUseCase removeSpreadLevelFromAccountUseCase;

  @GetMapping
  @ResponseBody
  public List<AccountDto> getAllAccounts() {
    return this.accountRepository.findAllByActiveTrue().stream()
        .map(AccountDto::fromAccount)
        .toList();
  }

  @PostMapping
  @ResponseBody
  public AccountDto createAccount(@RequestBody CreateAccountRequestDto dto) {
    return AccountDto.fromAccount(
        createAccountUseCase.execute(
            new CreateAccountUseCaseDto(
                UUID.randomUUID(),
                dto.customerId(),
                dto.name(),
                Currency.getInstance(dto.currency()),
                dto.iban())));
  }

  @GetMapping("/{id}")
  @ResponseBody
  public AccountDto getAccountById(@PathVariable("id") UUID id) {
    return AccountDto.fromAccount(accountRepository.getReferenceById(id));
  }

  @PutMapping("/{id}/add-spread-level")
  @ResponseBody
  public AccountDto addSpreadLevel(
      @PathVariable("id") UUID id, @RequestBody SpreadLevelDto spreadLevelDto) {
    return AccountDto.fromAccount(
        addSpreadLevelToAccountUseCase.execute(
            new AddSpreadLevelToAccountUseCaseDto(
                id, spreadLevelDto.threshold(), spreadLevelDto.commission())));
  }

  @PutMapping("/{id}/update-spread-level")
  @ResponseBody
  public AccountDto updateSpreadLevel(
      @PathVariable("id") UUID id, @RequestBody SpreadLevelDto spreadLevelDto) {
    return AccountDto.fromAccount(
        updateSpreadLevelInAccountUseCase.execute(
            new UpdateSpreadLevelInAccountUseCaseDto(
                id, spreadLevelDto.threshold(), spreadLevelDto.commission())));
  }

  @DeleteMapping("/{id}/remove-spread-level")
  @ResponseBody
  public AccountDto removeSpreadLevel(
      @PathVariable("id") UUID id, @RequestBody SpreadLevelDto spreadLevelDto) {
    return AccountDto.fromAccount(
        removeSpreadLevelFromAccountUseCase.execute(
            new RemoveSpreadLevelFromAccountUseCaseDto(id, spreadLevelDto.threshold())));
  }
}
