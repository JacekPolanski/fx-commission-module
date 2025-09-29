package com.bank.fx_commission.customer.application;

import com.bank.fx_commission.customer.domain.Customer;
import com.bank.fx_commission.customer.domain.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.bank.fx_commission.shared.UseCase;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class DeactivateCustomerUseCase implements UseCase<UUID, Customer> {
    private final CustomerRepository repository;

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
