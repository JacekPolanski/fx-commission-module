package com.bank.fx_commission_module.customer.ui.controller;

import com.bank.fx_commission_module.customer.application.CreateCustomerUseCase;
import com.bank.fx_commission_module.customer.application.CreateCustomerUseCaseDto;
import com.bank.fx_commission_module.customer.application.DeactivateCustomerUseCase;
import com.bank.fx_commission_module.customer.ui.dto.CreateCustomerDto;
import com.bank.fx_commission_module.customer.domain.Customer;
import com.bank.fx_commission_module.customer.domain.CustomerRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    private final CustomerRepository customerRepository;
    private final CreateCustomerUseCase createCustomerUseCase;
    private final DeactivateCustomerUseCase deactivateCustomerUseCase;

    public CustomerController(
            CustomerRepository customerRepository,
            CreateCustomerUseCase createCustomerUseCase,
            DeactivateCustomerUseCase deactivateCustomerUseCase
    ) {
        this.customerRepository = customerRepository;
        this.createCustomerUseCase = createCustomerUseCase;
        this.deactivateCustomerUseCase = deactivateCustomerUseCase;
    }

    @GetMapping
    public List<Customer> getAllCustomers() {
        return this.customerRepository.findAll();
    }

    @PostMapping
    public Customer createCustomer(CreateCustomerDto customer) {
        return this.createCustomerUseCase.execute(new CreateCustomerUseCaseDto(UUID.randomUUID(), customer.name()));
    }

    @PostMapping("/{id}/deactivate")
    public void deactivate(@PathVariable UUID id) {
        this.deactivateCustomerUseCase.execute(id);
    }
}
