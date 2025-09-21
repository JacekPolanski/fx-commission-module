package com.bank.fx_commission.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {
    Account findByIban(String iban);
    List<Account> findAllByActiveTrue();
}
