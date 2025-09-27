package com.bank.fx_commission.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TechnicalAccountLedgerRepository extends JpaRepository<TechnicalAccountLedger, UUID> {
}
