package com.bank.fxcommission.shared.customer;

import java.util.UUID;

public interface CustomerFacade {
  boolean isCustomerActive(UUID customerId);
}
