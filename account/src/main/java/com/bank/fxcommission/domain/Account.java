package com.bank.fxcommission.domain;

import com.bank.fxcommission.domain.strategies.CommissionStrategy;
import com.bank.fxcommission.domain.strategies.FixedStrategy;
import com.bank.fxcommission.domain.strategies.NoCommissionStrategy;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Account implements com.bank.fxcommission.shared.account.Account {
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
      case PERCENTAGE_OF_TRANSACTIONS_COUNT_MONTHLY ->
          strategy = new NoCommissionStrategy(); // ToDo: implement
      case PERCENTAGE_OF_TRANSACTION_AMOUNT ->
          strategy = new NoCommissionStrategy(); // ToDo: implement
      default -> strategy = new NoCommissionStrategy();
    }

    return strategy;
  }

  public void subtractFromTheBalance(BigDecimal amountInBaseCurrency)
      throws IllegalArgumentException {
    if (this.balance.compareTo(amountInBaseCurrency) < 0) {
      throw new IllegalArgumentException("Insufficient funds");
    }

    this.balance = this.balance.subtract(amountInBaseCurrency);
  }

  public void addToTheBalance(BigDecimal amount) {
    this.balance = this.balance.add(amount);
  }
}
