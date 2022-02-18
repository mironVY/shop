package com.mironovvlad.shop.rest.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PurchaseModel {
    private String name;
    private BigDecimal price;
    private Integer number;
}
