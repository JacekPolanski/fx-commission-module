package com.bank.fxcommission.domain;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TechnicalAccountLedgerRepository
    extends JpaRepository<TechnicalAccountLedger, UUID> {}
