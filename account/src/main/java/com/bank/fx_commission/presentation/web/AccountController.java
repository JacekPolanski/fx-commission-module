package com.bank.fx_commission.presentation.web;

import com.bank.fx_commission.application.CreateAccountUseCase;
import com.bank.fx_commission.application.dto.CreateAccountUseCaseDTO;
import com.bank.fx_commission.domain.Account;
import com.bank.fx_commission.presentation.web.dto.CreateAccountDTO;
import org.springframework.web.bind.annotation.*;

import java.util.Currency;
import java.util.UUID;

@RestController
@RequestMapping("/api/account")
public class AccountController {
    private final CreateAccountUseCase createAccountUseCase;

    AccountController(CreateAccountUseCase createAccountUseCase) {
        this.createAccountUseCase = createAccountUseCase;
    }

    @PostMapping
    @ResponseBody
    public Account createAccount(@RequestBody CreateAccountDTO dto) {
        return createAccountUseCase.execute(new CreateAccountUseCaseDTO(
                UUID.randomUUID(),
                dto.customerId(),
                dto.name(),
                Currency.getInstance(dto.currency()),
                dto.iban()
        ));
    }
}
