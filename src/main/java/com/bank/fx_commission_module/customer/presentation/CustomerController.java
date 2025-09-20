package com.bank.fx_commission_module.customer.presentation;

import com.bank.fx_commission_module.customer.application.CreateCustomerUseCase;
import com.bank.fx_commission_module.customer.application.CreateCustomerUseCaseDTO;
import com.bank.fx_commission_module.customer.application.DeactivateCustomerUseCase;
import com.bank.fx_commission_module.customer.application.GetAllCustomersQuery;
import com.bank.fx_commission_module.customer.persistence.Customer;
import com.bank.fx_commission_module.customer.persistence.CustomerDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    private final CreateCustomerUseCase createCustomerUseCase;
    private final DeactivateCustomerUseCase deactivateCustomerUseCase;
    private final GetAllCustomersQuery getAllCustomersQuery;

    public CustomerController(
            CreateCustomerUseCase createCustomerUseCase,
            DeactivateCustomerUseCase deactivateCustomerUseCase,
            GetAllCustomersQuery  getAllCustomersQuery
    ) {
        this.createCustomerUseCase = createCustomerUseCase;
        this.deactivateCustomerUseCase = deactivateCustomerUseCase;
        this.getAllCustomersQuery = getAllCustomersQuery;
    }

    @GetMapping
    @ResponseBody
    public List<CustomerDTO> getAllCustomers() {
        return this.getAllCustomersQuery.execute();
    }

    @PostMapping
    @ResponseBody
    public Customer createCustomer(@RequestBody CreateCustomerDTO customer) {
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
