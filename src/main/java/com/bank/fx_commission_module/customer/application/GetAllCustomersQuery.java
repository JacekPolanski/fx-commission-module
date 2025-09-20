package com.bank.fx_commission_module.customer.application;

import com.bank.fx_commission_module.customer.domain.dto.CustomerDTO;
import com.bank.fx_commission_module.customer.domain.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetAllCustomersQuery {
    private final CustomerRepository customerRepository;

    public GetAllCustomersQuery(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<CustomerDTO> execute() {
        return this.customerRepository.findAllByActiveTrue()
                .stream()
                .map(c -> new CustomerDTO(c.getId(), c.getName()))
                .collect(Collectors.toList());

    }
}
