package com.bank.fxcommission.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.UUID;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TechnicalAccountLedger {
    @Id
    private UUID id;
    private UUID transactionId;
    private UUID accountId;
    @Builder.Default
    private BigDecimal debit = BigDecimal.ZERO;
    @Builder.Default
    private BigDecimal credit = BigDecimal.ZERO;
    @Builder.Default
    @Column(length = 3)
    private Currency currency = Currency.getInstance("EUR");
    private String description;
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();
}
