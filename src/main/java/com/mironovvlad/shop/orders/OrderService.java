package com.mironovvlad.shop.orders;

import com.mironovvlad.shop.rest.dto.Order;
import com.mironovvlad.shop.rest.dto.OrderModel;

import java.util.List;

public interface OrderService {

     void addOrder(Order order);
     void changeOrderStatus(Long id, OrderStatus status);
     void deleteOrder(Long id);
     List<OrderModel> getOrders();
     OrderModel getOrder(Long id);
}
