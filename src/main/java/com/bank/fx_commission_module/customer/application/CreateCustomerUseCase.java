package com.bank.fx_commission_module.customer.application;

import com.bank.fx_commission_module.customer.domain.Customer;
import com.bank.fx_commission_module.customer.domain.CustomerRepository;

import java.time.LocalDateTime;

public class CreateCustomerUseCase {
    private final CustomerRepository customerRepository;

    public CreateCustomerUseCase(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer execute(CreateCustomerUseCaseDto customerDto) {
        Customer customer = Customer.builder()
                .id(customerDto.id())
                .name(customerDto.name())
                .active(true)
                .createdAt(LocalDateTime.now())
                .build();

        this.customerRepository.save(customer);

        return customer;
    }
}
