package com.mironovvlad.shop.rest.dto;

import com.mironovvlad.shop.orders.Delivery;
import com.mironovvlad.shop.orders.OrderStatus;
import com.mironovvlad.shop.orders.Payment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Schema
public class OrderModel {
    private Long id;
    private String name;
    private String number;
    private Delivery delivery;
    private String deliveryAddress;
    private LocalDateTime deliveryTime;
    private Payment payment;
    private List<PurchaseModel> PurchaseList;
    private OrderStatus orderStatus;
}
