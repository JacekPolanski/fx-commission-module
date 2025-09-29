package com.bank.fxcommission.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.ArrayList;

@Entity
@Getter
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
    this.spreadLevels.add(new SpreadLevel(threshold, commission));
    return this;
  }
}
