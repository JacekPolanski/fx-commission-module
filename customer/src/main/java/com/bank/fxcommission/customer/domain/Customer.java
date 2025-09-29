package com.bank.fxcommission.customer.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


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
