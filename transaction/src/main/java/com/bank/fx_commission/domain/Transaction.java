package com.bank.fx_commission.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.lang.reflect.Method;
import java.math.BigDecimal;
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
    private BigDecimal amount;
    private BigDecimal amountInDestinationCurrency;
    @Builder.Default
    private BigDecimal commission = BigDecimal.ZERO;
    private BigDecimal rate;
    private String title;
    private LocalDateTime createdAt;
    @Setter
    private LocalDateTime updatedAt;

    public Transaction calculateAccountInDestinationCurrency() {
        this.amountInDestinationCurrency = this.amount.multiply(this.rate);

        return this;
    }
}
