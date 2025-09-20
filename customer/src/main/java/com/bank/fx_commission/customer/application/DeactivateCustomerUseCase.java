package com.bank.fx_commission.customer.application;

import com.bank.fx_commission.customer.domain.Customer;
import com.bank.fx_commission.customer.domain.CustomerRepository;
import org.springframework.stereotype.Service;
import com.bank.fx_commission.shared.UseCase;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
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
