package com.bank.fx_commission_module.customer.business;

import com.bank.fx_commission_module.customer.persistence.Customer;
import com.bank.fx_commission_module.customer.persistence.CustomerRepository;
import com.bank.fx_commission_module.shared.UseCase;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class DeactivateCustomerUseCase implements UseCase<UUID, Customer> {
    private final CustomerRepository repository;

    public DeactivateCustomerUseCase(CustomerRepository customerRepository) {
        this.repository = customerRepository;
    }

    public Customer execute(UUID customerId) {
        Optional<Customer> customer = this.repository.findById(customerId);
        if (customer.isPresent()) {
            customer.get().deactivate();
            this.repository.save(customer.get());
        } else {
            throw new IllegalArgumentException("Customer with id " + customerId + " not found");
        }

        return customer.get();
    }
}
