package com.adgarsolutions.shared.model;

import com.adgarsolutions.shared.repository.Identifiable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order implements Identifiable<String> {
    private String id;
    private String symbol;
    private BigDecimal price;
    private BigDecimal shares;
    private String side;
}
