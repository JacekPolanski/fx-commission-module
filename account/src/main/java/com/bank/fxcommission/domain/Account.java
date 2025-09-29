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
@Setter
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

  public Account setSpreadType(SpreadType spreadType) {
    this.spread.setType(spreadType);

    return this;
  }
}
