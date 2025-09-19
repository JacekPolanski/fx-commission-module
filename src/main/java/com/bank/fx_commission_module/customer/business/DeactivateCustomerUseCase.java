package com.bank.fx_commission_module.customer.business;

import com.bank.fx_commission_module.customer.persistence.Customer;
import com.bank.fx_commission_module.customer.persistence.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class DeactivateCustomerUseCase {
    private final CustomerRepository repository;

    public DeactivateCustomerUseCase(CustomerRepository customerRepository) {
        this.repository = customerRepository;
    }

    public void execute(UUID customerId) {
        Optional<Customer> customer = this.repository.findById(customerId);
        if (customer.isPresent()) {
            customer.get().deactivate();
            this.repository.save(customer.get());
        } else {
            throw new IllegalArgumentException("Customer with id " + customerId + " not found");
        }
    }
}
