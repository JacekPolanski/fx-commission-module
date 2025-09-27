package com.bank.fx_commission.application.listener;

import com.bank.fx_commission.domain.TechnicalAccountLedger;
import com.bank.fx_commission.domain.TechnicalAccountLedgerRepository;
import com.bank.fx_commission.shared.event.TransactionApprovedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class CommissionSavingListener{
    private final TechnicalAccountLedgerRepository repository;

    @ApplicationModuleListener
    public void on(TransactionApprovedEvent event) {
        log.info("CommissionSavingListener received event: transactionId={}, sourceAccountId={}, commission={}, currency={}",
                event.getSource().getId(),
                event.getSource().getSourceAccountId(),
                event.getSource().getCommission(),
                event.getSource().getReferenceCurrency());

        TechnicalAccountLedger ledger = TechnicalAccountLedger.builder()
                .id(UUID.randomUUID())
                .transactionId(event.getSource().getId())
                .accountId(event.getSource().getSourceAccountId())
                .credit(event.getSource().getCommission())
                .currency(event.getSource().getReferenceCurrency())
                .description("Commission for transaction")
                .build();

        try {
            this.repository.save(ledger);
            log.info("Commission ledger saved: id={}, transactionId={}", ledger.getId(), ledger.getTransactionId());
        } catch (Exception e) {
            log.error("Failed to save commission ledger for transactionId={}", ledger.getTransactionId(), e);
            throw e;
        }
    }
}
