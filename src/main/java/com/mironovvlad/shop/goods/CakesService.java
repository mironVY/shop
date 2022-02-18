package com.mironovvlad.shop.goods;

import com.mironovvlad.shop.rest.dto.CakeDetail;
import com.mironovvlad.shop.rest.dto.Cakes;

public interface CakesService {
    Cakes getCakes();
    CakeDetail getCake(Long id);
    CakeEntity getCakeEntity(Long id);
    Long addCake(CakeDetail cake);
    void deleteCake(Long id);
}
