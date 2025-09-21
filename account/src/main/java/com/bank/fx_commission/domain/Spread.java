package com.bank.fx_commission.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Getter
public class Spread {
    @Id
    private UUID id;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private List<SpreadLevel> spreadLevels;

    public Map<Integer, BigDecimal> getSpreadLevels() {
        return spreadLevels.stream()
                .collect(Collectors.toMap(
                        SpreadLevel::threshold,
                        SpreadLevel::commission
                ));
    }
}
