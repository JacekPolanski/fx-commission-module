package com.bank.fxcommission.domain;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, UUID> {
  Account findByIban(String iban);

  List<Account> findAllByActiveTrue();
}
