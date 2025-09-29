package com.bank.fxcommission.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Transaction implements com.bank.fxcommission.shared.transcation.Transaction {
  @Id private UUID id;
  private UUID sourceAccountId;
  private UUID destinationAccountId;

  @Column(length = 3, nullable = false)
  private Currency baseCurrency;

  @Column(length = 3, nullable = false)
  private Currency quoteCurrency;

  @Column(length = 3, nullable = false)
  private Currency referenceCurrency;

  private BigDecimal amountInReferenceCurrency;
  private BigDecimal amountInBaseCurrency;
  private BigDecimal amountInQuoteCurrency;
  @Builder.Default private BigDecimal commission = BigDecimal.ZERO;
  private BigDecimal baseToReferenceRate;
  private BigDecimal baseToQuoteRate;
  private String title;
  private LocalDateTime createdAt;
  @Setter private LocalDateTime updatedAt;

  @Builder.Default
  @JdbcTypeCode(SqlTypes.VARCHAR)
  private TransactionStatus status = TransactionStatus.PENDING;

  public Transaction calculateAmountInQuoteCurrency() {
    this.amountInQuoteCurrency =
        this.amountInBaseCurrency.multiply(this.baseToQuoteRate).setScale(2, RoundingMode.HALF_UP);

    return this;
  }

  public Transaction calculateAmountInReferenceCurrency() {
    this.amountInReferenceCurrency =
        this.amountInBaseCurrency
            .multiply(this.baseToReferenceRate)
            .setScale(2, RoundingMode.HALF_UP);

    return this;
  }

  public void approve() {
    if (this.status != TransactionStatus.PENDING) {
      throw new IllegalStateException("Transaction already approved");
    }

    this.status = TransactionStatus.APPROVED;
    this.updatedAt = LocalDateTime.now();
  }

  public Transaction applyCommission(BigDecimal commission) {
    this.commission = commission;
    this.amountInBaseCurrency = this.amountInBaseCurrency.add(commission);

    return this;
  }
}
