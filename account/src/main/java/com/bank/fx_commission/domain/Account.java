package com.bank.fx_commission.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Account {
    @Setter(AccessLevel.NONE)
    @Id
    private UUID id;
    private UUID customerId;
    private String name;
    private String currency;
    @Setter(AccessLevel.NONE)
    private int balance;
    @Setter(AccessLevel.NONE)
    private boolean active;
    private String number;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
