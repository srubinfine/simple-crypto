package com.adgarsolutions.shared.model;

import com.adgarsolutions.shared.repository.Identifiable;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Builder
public class Order implements Identifiable<String> {
    @NonNull
    private String id;
    @NonNull
    private String symbol;
    @NonNull
    private BigDecimal price;
    @NonNull
    private BigDecimal shares;
    @NonNull
    private String side;
    private Boolean isDeleted = false;
    private OffsetDateTime updated_time;

    @Override
    public Map<String, Object> getFieldMappings() {
        return new LinkedHashMap<>()  {{
            put("id", id);
            put("symbol", symbol);
            put("price", price.floatValue());
            put("shares", shares.floatValue());
            put("side", side);
        }};
    }
}
