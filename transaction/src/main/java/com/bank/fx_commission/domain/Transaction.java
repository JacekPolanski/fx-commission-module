package com.bank.fx_commission.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.UUID;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Transaction {
    @Id
    private UUID id;
    private UUID sourceAccountId;
    private UUID destinationAccountId;
    @Column(length = 3, nullable = false)
    private Currency sourceCurrency;
    @Column(length = 3, nullable = false)
    private Currency destinationCurrency;
    @Column(length = 3, nullable = false)
    private Currency referenceCurrency;
    private BigDecimal amountInReferenceCurrency;
    private BigDecimal amountInSourceCurrency;
    private BigDecimal amountInDestinationCurrency;
    @Builder.Default
    private BigDecimal commission = BigDecimal.ZERO;
    private BigDecimal referenceRate;
    private BigDecimal rate;
    private String title;
    private LocalDateTime createdAt;
    @Setter
    private LocalDateTime updatedAt;

    public Transaction calculateAmountInDestinationCurrency() {
        this.amountInDestinationCurrency = this.amountInSourceCurrency.divide(this.rate, 2, RoundingMode.HALF_UP);

        return this;
    }

    public Transaction calculateAmountInReferenceCurrency() {
        this.amountInReferenceCurrency = this.amountInSourceCurrency.divide(this.referenceRate, 2, RoundingMode.HALF_UP);

        return this;
    }
}
