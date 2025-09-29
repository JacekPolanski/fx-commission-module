package com.bank.fxcommission.customer.application;

import com.bank.fxcommission.customer.application.dto.CreateCustomerUseCaseDTO;
import com.bank.fxcommission.customer.domain.Customer;
import com.bank.fxcommission.customer.domain.CustomerRepository;
import com.bank.fxcommission.shared.UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class CreateCustomerUseCase implements UseCase<CreateCustomerUseCaseDTO, Customer> {
    private final CustomerRepository customerRepository;

    public Customer execute(CreateCustomerUseCaseDTO customerDto) {
        Customer customer = Customer.builder()
                .id(customerDto.id())
                .name(customerDto.name())
                .active(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        customerRepository.save(customer);

        return customer;
    }
}
