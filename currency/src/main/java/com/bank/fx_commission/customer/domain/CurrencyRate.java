package com.bank.fx_commission.customer.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

import java.math.BigDecimal;

@Entity
@Getter
public class CurrencyRate {
    @Id
    private String currencyCode;
    private BigDecimal rate;
}
