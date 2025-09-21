package com.bank.fx_commission.shared.customer;

import java.util.UUID;

public interface CustomerFacade {
    boolean isCustomerActive(UUID customerId);
}
