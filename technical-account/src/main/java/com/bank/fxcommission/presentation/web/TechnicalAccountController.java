package com.bank.fxcommission.presentation.web;

import com.bank.fxcommission.domain.TechnicalAccountLedgerRepository;
import com.bank.fxcommission.presentation.web.dto.TechnicalAccountDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping("/api/technical-account")
public class TechnicalAccountController {
  @Autowired
  private TechnicalAccountLedgerRepository ledgerRepository;

  @GetMapping
  @ResponseBody
  public List<TechnicalAccountDto> getAll() {
    return ledgerRepository.findAll().stream().map(TechnicalAccountDto::fromLedger).toList();
  }
}
