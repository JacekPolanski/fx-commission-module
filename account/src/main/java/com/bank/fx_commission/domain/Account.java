package com.bank.fx_commission.domain;

import com.bank.fx_commission.domain.strategies.CommissionStrategy;
import com.bank.fx_commission.domain.strategies.FixedStrategy;
import com.bank.fx_commission.domain.strategies.NoCommissionStrategy;
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
public class Account implements com.bank.fx_commission.shared.account.Account{
    @Setter(AccessLevel.NONE)
    @Id
    private UUID id;
    private UUID customerId;
    private String name;
    @Column(length = 3, nullable = false)
    private Currency currency;
    @Setter(AccessLevel.NONE)
    private BigDecimal balance;
    @Setter(AccessLevel.NONE)
    private boolean active;
    private String iban;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "spread_id")
    private Spread spread;

    @Override
    public boolean isBelongsToCustomer(UUID customerId) {
        return this.customerId.equals(customerId);
    }

    @Override
    public UUID id() {
        return this.id;
    }

    public CommissionStrategy getSpreadStrategy() {
        CommissionStrategy strategy;
        switch (this.spread.getType()) {
            case FIXED -> strategy = new FixedStrategy();
            case PERCENTAGE_OF_TRANSACTIONS_COUNT_MONTHLY -> strategy = new NoCommissionStrategy(); // ToDo: implement
            case PERCENTAGE_OF_TRANSACTION_AMOUNT -> strategy = new NoCommissionStrategy(); // ToDo: implement
            default -> strategy = new NoCommissionStrategy();
        }

        return strategy;
    }

    public void subtractFromTheBalance(BigDecimal amountInBaseCurrency) throws IllegalArgumentException {
        if (this.balance.compareTo(amountInBaseCurrency) < 0) {
            throw new IllegalArgumentException("Insufficient funds");
        }

        this.balance = this.balance.subtract(amountInBaseCurrency);
    }

    public void addToTheBalance(BigDecimal amount) {
        this.balance = this.balance.add(amount);
    }
}
