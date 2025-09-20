package com.bank.fx_commission.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Setter;

import java.util.UUID;

@Entity
public class Account {
    @Setter(AccessLevel.NONE)
    @Id
    private UUID id;
}
