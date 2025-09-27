package com.bank.fx_commission.shared.event;

import com.bank.fx_commission.shared.transcation.Transaction;
import org.springframework.context.ApplicationEvent;

public class TransactionApprovedEvent extends ApplicationEvent {
    public TransactionApprovedEvent(Transaction source) {
        super(source);
    }

    public Transaction getSource() {
        return (Transaction) super.getSource();
    }
}
