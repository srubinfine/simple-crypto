package com.adgarsolutions.shared.model;

import com.adgarsolutions.shared.repository.Identifiable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order implements Identifiable<String> {
    private String id;
    private String symbol;
    private BigDecimal price;
    private BigDecimal shares;
    private String side;

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
