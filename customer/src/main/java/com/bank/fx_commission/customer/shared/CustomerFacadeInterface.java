package com.bank.fx_commission.customer.shared;

import com.bank.fx_commission.customer.domain.Customer;
import com.bank.fx_commission.customer.domain.CustomerRepository;
import com.bank.fx_commission.shared.customer.CustomerFacade;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerFacadeInterface implements CustomerFacade {
    private final CustomerRepository repository;

    CustomerFacadeInterface(CustomerRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean isCustomerActive(UUID customerId) {
        Optional<Customer> customer = this.repository.findById(customerId);

        return customer.map(Customer::isActive).orElse(false);
    }
}
