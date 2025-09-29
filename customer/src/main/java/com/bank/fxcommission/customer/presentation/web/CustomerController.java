package com.bank.fxcommission.customer.presentation.web;

import com.bank.fxcommission.customer.application.CreateCustomerUseCase;
import com.bank.fxcommission.customer.application.dto.CreateCustomerUseCaseDTO;
import com.bank.fxcommission.customer.application.DeactivateCustomerUseCase;
import com.bank.fxcommission.customer.domain.Customer;
import com.bank.fxcommission.customer.domain.CustomerRepository;
import com.bank.fxcommission.customer.presentation.web.dto.CustomerDTO;
import com.bank.fxcommission.customer.presentation.web.dto.CreateCustomerRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CreateCustomerUseCase createCustomerUseCase;
    private final DeactivateCustomerUseCase deactivateCustomerUseCase;
    private final CustomerRepository customerRepository;

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
