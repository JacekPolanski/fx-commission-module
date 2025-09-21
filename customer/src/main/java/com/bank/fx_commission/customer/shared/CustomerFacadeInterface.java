package com.bank.fx_commission.customer.shared;

import com.bank.fx_commission.customer.domain.Customer;
import com.bank.fx_commission.customer.domain.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CustomerFacadeInterface implements com.bank.fx_commission.shared.customer.CustomerFacadeInterface {
    private final CustomerRepository repository;

    CustomerFacadeInterface(CustomerRepository repository) {
        this.repository = repository;
    }

    public Customer getCustomerById(UUID customerId) {
        return this.repository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer with id " + customerId + " not found"));
    }
}
