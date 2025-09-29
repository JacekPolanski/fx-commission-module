package com.bank.fxcommission.customer.shared;

import com.bank.fxcommission.customer.domain.Customer;
import com.bank.fxcommission.customer.domain.CustomerRepository;
import com.bank.fxcommission.shared.customer.CustomerFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerFacadeInterface implements CustomerFacade {
    private final CustomerRepository repository;

    @Override
    public boolean isCustomerActive(UUID customerId) {
        Optional<Customer> customer = this.repository.findById(customerId);

        return customer.map(Customer::isActive).orElse(false);
    }
}
