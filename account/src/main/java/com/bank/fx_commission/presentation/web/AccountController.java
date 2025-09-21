package com.bank.fx_commission.presentation.web;

import com.bank.fx_commission.application.CreateAccountUseCase;
import com.bank.fx_commission.application.dto.CreateAccountUseCaseDTO;
import com.bank.fx_commission.domain.Account;
import com.bank.fx_commission.domain.AccountRepository;
import com.bank.fx_commission.presentation.web.dto.AccountDTO;
import com.bank.fx_commission.presentation.web.dto.CreateAccountRequestDTO;
import org.springframework.web.bind.annotation.*;

import java.util.Currency;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/account")
public class AccountController {
    private final CreateAccountUseCase createAccountUseCase;
    private final AccountRepository accountRepository;

    AccountController(CreateAccountUseCase createAccountUseCase, AccountRepository accountRepository) {
        this.createAccountUseCase = createAccountUseCase;
        this.accountRepository = accountRepository;
    }

    @GetMapping
    @ResponseBody
    public List<AccountDTO> getAllAccounts() {
        return this.accountRepository.findAllByActiveTrue()
                .stream()
                .map(a -> new AccountDTO(
                        a.getId(),
                        a.getName(),
                        a.getIban(),
                        a.getCurrency().getCurrencyCode(),
                        a.getBalance().toString(),
                        a.getSpread().getSpreadLevels()
                ))
                .toList();
    }

    @PostMapping
    @ResponseBody
    public Account createAccount(@RequestBody CreateAccountRequestDTO dto) {
        return createAccountUseCase.execute(new CreateAccountUseCaseDTO(
                UUID.randomUUID(),
                dto.customerId(),
                dto.name(),
                Currency.getInstance(dto.currency()),
                dto.iban()
        ));
    }
}
