package com.bank.fx_commission_module.customer.application;

import com.bank.fx_commission_module.customer.domain.Customer;
import com.bank.fx_commission_module.customer.domain.CustomerRepository;
import com.bank.fx_commission_module.shared.UseCase;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CreateCustomerUseCase implements UseCase<CreateCustomerUseCaseDTO, Customer> {
    private final CustomerRepository customerRepository;

    public CreateCustomerUseCase(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer execute(CreateCustomerUseCaseDTO customerDto) {
        Customer customer = Customer.builder()
                .id(customerDto.id())
                .name(customerDto.name())
                .active(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        this.customerRepository.save(customer);

        return customer;
    }
}
