package com.adgarsolutions.shared.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private String id;
    private String symbol;
    private BigDecimal price;
    private BigDecimal shares;
    private String side;
}
