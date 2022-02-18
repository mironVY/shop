package com.mironovvlad.shop.rest.dto;

import com.mironovvlad.shop.orders.OrderStatus;
import lombok.Data;

@Data
public class OrderChange {
    private OrderStatus status;
}
