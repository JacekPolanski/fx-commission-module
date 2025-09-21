package com.bank.fx_commission.customer.domain;

import com.bank.fx_commission.shared.customer.CustomerInterface;
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
public class Customer implements CustomerInterface {
    @Setter(AccessLevel.NONE)
    @Id
    private UUID id;
    private String name;
    @Getter(AccessLevel.NONE)
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
