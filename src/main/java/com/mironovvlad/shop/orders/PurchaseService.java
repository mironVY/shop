package com.mironovvlad.shop.orders;

import com.mironovvlad.shop.goods.CakeEntity;

public interface PurchaseService {

    void addPurchase(OrderEntity orderEntity, CakeEntity cakeEntity,Integer number);

}
