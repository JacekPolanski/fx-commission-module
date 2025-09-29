package com.bank.fxcommission.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Spread {
  @Id private UUID id;

  @JdbcTypeCode(SqlTypes.VARCHAR)
  private SpreadType type;

  @Builder.Default
  @JdbcTypeCode(SqlTypes.JSON)
  @Column(columnDefinition = "jsonb")
  private List<SpreadLevel> spreadLevels = new ArrayList<>();

  public Map<Integer, BigDecimal> getSpreadLevels() {
    return spreadLevels.stream()
        .collect(Collectors.toMap(SpreadLevel::threshold, SpreadLevel::commission));
  }

  public Spread addSpreadLevel(int threshold, BigDecimal commission) {
    if (this.spreadLevels.stream().anyMatch(level -> level.threshold() == threshold)) {
      throw new IllegalArgumentException(
          "Spread level with the given threshold already exists: " + threshold);
    }
    this.spreadLevels.add(new SpreadLevel(threshold, commission));
    return this;
  }

  public Spread updateSpreadLevel(int threshold, BigDecimal commission) {
    final ListIterator<SpreadLevel> it = this.spreadLevels.listIterator();
    while (it.hasNext()) {
      final SpreadLevel level = it.next();
      if (level.threshold() == threshold) {
        it.set(new SpreadLevel(threshold, commission));
        return this;
      }
    }
    throw new IllegalArgumentException(
        "Spread level with the given threshold does not exist: " + threshold);
  }

  public Spread removeSpreadLevel(int threshold) {
    final ListIterator<SpreadLevel> it = this.spreadLevels.listIterator();
    while (it.hasNext()) {
      final SpreadLevel level = it.next();
      if (level.threshold() == threshold) {
        it.remove();
        return this;
      }
    }
    throw new IllegalArgumentException(
        "Spread level with the given threshold does not exist: " + threshold);
  }
}
