package com.bank.fxcommission.customer.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.math.BigDecimal;
import lombok.Getter;

@Entity
@Getter
public class CurrencyRate {
  @Id private String currencyCode;
  private BigDecimal rate;
}
