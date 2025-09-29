package com.bank.fxcommission.customer.application;

import com.bank.fxcommission.customer.domain.Customer;
import com.bank.fxcommission.customer.domain.CustomerRepository;
import com.bank.fxcommission.shared.UseCase;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DeactivateCustomerUseCase implements UseCase<UUID, Customer> {
  private final CustomerRepository repository;

  public Customer execute(UUID customerId) {
    final Optional<Customer> customer = this.repository.findById(customerId);
    if (customer.isPresent()) {
      customer.get().deactivate();
      this.repository.save(customer.get());
    } else {
      throw new IllegalArgumentException("Customer with id " + customerId + " not found");
    }

    return customer.get();
  }
}
