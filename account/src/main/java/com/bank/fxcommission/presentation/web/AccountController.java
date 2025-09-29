package com.bank.fxcommission.presentation.web;

import com.bank.fxcommission.application.*;
import com.bank.fxcommission.application.dto.*;
import com.bank.fxcommission.domain.AccountRepository;
import com.bank.fxcommission.domain.SpreadType;
import com.bank.fxcommission.presentation.web.dto.AccountDto;
import com.bank.fxcommission.presentation.web.dto.CreateAccountRequestDto;
import com.bank.fxcommission.presentation.web.dto.SpreadLevelDto;
import com.bank.fxcommission.presentation.web.dto.UpdateAccountDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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
  private final RemoveSpreadLevelFromAccountUseCase removeSpreadLevelFromAccountUseCase;
  private final UpdateAccountUseCase updateAccountUseCase;

  @GetMapping
  @ResponseBody
  public List<AccountDto> getAllAccounts() {
    try {
      return this.accountRepository.findAllByActiveTrue().stream()
          .map(AccountDto::fromAccount)
          .toList();
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
    }
  }

  @PostMapping
  @ResponseBody
  public AccountDto createAccount(@RequestBody CreateAccountRequestDto dto) {
    try {
      return AccountDto.fromAccount(
          createAccountUseCase.execute(
              new CreateAccountUseCaseDto(
                  UUID.randomUUID(),
                  dto.customerId(),
                  dto.name(),
                  Currency.getInstance(dto.currency()),
                  dto.iban())));
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
    }
  }

  @GetMapping("/{id}")
  @ResponseBody
  public AccountDto getAccountById(@PathVariable("id") UUID id) {
    try {
      return AccountDto.fromAccount(accountRepository.getReferenceById(id));
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
    }
  }

  @PutMapping("/{id}/add-spread-level")
  @ResponseBody
  public AccountDto addSpreadLevel(
      @PathVariable("id") UUID id, @RequestBody SpreadLevelDto spreadLevelDto) {
    try {
      return AccountDto.fromAccount(
          addSpreadLevelToAccountUseCase.execute(
              new AddSpreadLevelToAccountUseCaseDto(
                  id, spreadLevelDto.threshold(), spreadLevelDto.commission())));
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
    }
  }

  @DeleteMapping("/{id}/remove-spread-level")
  @ResponseBody
  public AccountDto removeSpreadLevel(
      @PathVariable("id") UUID id, @RequestBody SpreadLevelDto spreadLevelDto) {
    try {
      return AccountDto.fromAccount(
          removeSpreadLevelFromAccountUseCase.execute(
              new RemoveSpreadLevelFromAccountUseCaseDto(id, spreadLevelDto.threshold())));
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
    }
  }

  @PutMapping("/{id}/update")
  @ResponseBody
  public AccountDto updateAccount(
      @PathVariable("id") UUID id, @RequestBody UpdateAccountDto accountDto) {
    try {
      return AccountDto.fromAccount(
          this.updateAccountUseCase.execute(
              new UpdateAccountUseCaseDto(
                  id, accountDto.name(), SpreadType.valueOf(accountDto.spreadType()))));
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
    }
  }
}
