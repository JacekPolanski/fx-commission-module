package com.bank.fx_commission.customer.presentation.web;

import com.bank.fx_commission.customer.application.CreateCustomerUseCase;
import com.bank.fx_commission.customer.application.dto.CreateCustomerUseCaseDTO;
import com.bank.fx_commission.customer.application.DeactivateCustomerUseCase;
import com.bank.fx_commission.customer.domain.Customer;
import com.bank.fx_commission.customer.domain.CustomerRepository;
import com.bank.fx_commission.customer.presentation.web.dto.CustomerDTO;
import com.bank.fx_commission.customer.presentation.web.dto.CreateCustomerRequestDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    private final CreateCustomerUseCase createCustomerUseCase;
    private final DeactivateCustomerUseCase deactivateCustomerUseCase;
    private final CustomerRepository customerRepository;

    public CustomerController(
            CreateCustomerUseCase createCustomerUseCase,
            DeactivateCustomerUseCase deactivateCustomerUseCase,
            CustomerRepository customerRepository
    ) {
        this.createCustomerUseCase = createCustomerUseCase;
        this.deactivateCustomerUseCase = deactivateCustomerUseCase;
        this.customerRepository = customerRepository;
    }

    @GetMapping
    @ResponseBody
    public List<CustomerDTO> getAllCustomers() {
        return this.customerRepository.findAllByActiveTrue()
                .stream()
                .map(c -> new CustomerDTO(c.getId(), c.getName()))
                .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseBody
    public Customer createCustomer(@RequestBody CreateCustomerRequestDTO customer) {
        return this.createCustomerUseCase.execute(new CreateCustomerUseCaseDTO(UUID.randomUUID(), customer.name()));
    }

    @PatchMapping("/{id}/deactivate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Customer deactivate(@PathVariable UUID id) {
        try {
            return this.deactivateCustomerUseCase.execute(id);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
