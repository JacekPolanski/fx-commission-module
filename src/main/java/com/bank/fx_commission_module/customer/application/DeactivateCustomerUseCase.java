package com.bank.fx_commission_module.customer.application;

import com.bank.fx_commission_module.customer.domain.Customer;
import com.bank.fx_commission_module.customer.domain.CustomerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
            customer.get().setActive(false);
            customer.get().setUpdatedAt(LocalDateTime.now());

            this.repository.save(customer.get());
        }
    }
}
