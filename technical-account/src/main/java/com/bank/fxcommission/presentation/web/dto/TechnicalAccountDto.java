package com.bank.fxcommission.presentation.web.dto;

import com.bank.fxcommission.domain.TechnicalAccountLedger;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record TechnicalAccountDto(
        UUID id,
        UUID transactionId,
        BigDecimal credit,
        String currency,
        String description,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
  public static TechnicalAccountDto fromLedger(TechnicalAccountLedger technicalAccountLedger) {
    return new TechnicalAccountDto(
            technicalAccountLedger.getId(),
            technicalAccountLedger.getTransactionId(),
            technicalAccountLedger.getCredit(),
            technicalAccountLedger.getCurrency().getCurrencyCode(),
            technicalAccountLedger.getDescription(),
            technicalAccountLedger.getCreatedAt(),
            technicalAccountLedger.getUpdatedAt()
    );
  }
}
