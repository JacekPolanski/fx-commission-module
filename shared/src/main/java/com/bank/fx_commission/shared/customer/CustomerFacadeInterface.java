package com.bank.fx_commission.shared.customer;

import java.util.UUID;

public interface CustomerFacadeInterface {
    CustomerInterface getCustomerById(UUID customerId);
}
