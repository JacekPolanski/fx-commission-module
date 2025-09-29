package com.bank.fxcommission.shared.event;

import com.bank.fxcommission.shared.transcation.Transaction;
import org.springframework.context.ApplicationEvent;

public class TransactionApprovedEvent extends ApplicationEvent {
    public TransactionApprovedEvent(Transaction source) {
        super(source);
    }

    public Transaction getSource() {
        return (Transaction) super.getSource();
    }
}
