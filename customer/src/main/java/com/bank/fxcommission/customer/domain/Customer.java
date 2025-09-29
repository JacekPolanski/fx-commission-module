package com.bank.fxcommission.customer.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Customer implements com.bank.fxcommission.shared.customer.Customer {
    @Setter(AccessLevel.NONE)
    @Id
    private UUID id;
    private String name;
    private boolean active;
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private LocalDateTime createdAt;
    @Getter(AccessLevel.NONE)
    private LocalDateTime updatedAt;

    public void deactivate() {
        this.active = false;
        this.updatedAt = LocalDateTime.now();
    }

}
