package com.bank.fxcommission.shared.transcation;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

public interface Transaction {
  UUID getId();

  UUID getSourceAccountId();

  BigDecimal getCommission();

  Currency getReferenceCurrency();
}
