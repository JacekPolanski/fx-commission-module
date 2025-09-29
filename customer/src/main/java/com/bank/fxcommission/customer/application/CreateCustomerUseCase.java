package com.bank.fxcommission.customer.application;

import com.bank.fxcommission.customer.application.dto.CreateCustomerUseCaseDto;
import com.bank.fxcommission.customer.domain.Customer;
import com.bank.fxcommission.customer.domain.CustomerRepository;
import com.bank.fxcommission.shared.UseCase;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CreateCustomerUseCase implements UseCase<CreateCustomerUseCaseDto, Customer> {
  private final CustomerRepository customerRepository;

  public Customer execute(CreateCustomerUseCaseDto customerDto) {
    final Customer customer =
        Customer.builder()
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
