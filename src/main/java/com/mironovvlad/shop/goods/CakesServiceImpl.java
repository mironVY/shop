package com.mironovvlad.shop.goods;

import com.mironovvlad.shop.exception.CakeNotFoundException;
import com.mironovvlad.shop.rest.dto.Cake;
import com.mironovvlad.shop.rest.dto.CakeDetail;
import com.mironovvlad.shop.rest.dto.Cakes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CakesServiceImpl implements CakesService {

    private final CakeRepository cakeRepository;

    @Autowired
    public CakesServiceImpl(CakeRepository cakeRepository) {
        this.cakeRepository = cakeRepository;
    }

    @Override
    public Cakes getCakes(){
        List<CakeEntity> cakeEntityList = cakeRepository.findAll();
        List<Cake> cakeList = cakeEntityList.stream().map(c -> {
            Cake cake = new Cake();
            cake.setId(c.getId());
            cake.setCalories(c.getCalories());
            cake.setName(c.getName());
            cake.setImage(c.getImage());
            cake.setPrice(c.getPrice());
            cake.setWeight(c.getWeight());
            return cake;
        }).collect(Collectors.toList());
        Cakes cakes = new Cakes();
        cakes.setCakeList(cakeList);
        return cakes;
    }

    @Override
    public CakeDetail getCake(Long id) {
        return  cakeRepository.findById(id)
                .map(cakeEntity -> {
                    CakeDetail cake = new CakeDetail();
                    cake.setId(cakeEntity.getId());
                    cake.setCalories(cakeEntity.getCalories());
                    cake.setName(cakeEntity.getName());
                    cake.setImage(cakeEntity.getImage());
                    cake.setPrice(cakeEntity.getPrice());
                    cake.setWeight(cakeEntity.getWeight());
                    cake.setDescription(cakeEntity.getDescription());
                    cake.setShelfLife(cakeEntity.getShelfLife());
                    return cake;
                })
                .orElseThrow(() -> new CakeNotFoundException("No such cake"));
    }

    @Override
    public CakeEntity getCakeEntity(Long id) {
        return cakeRepository.findById(id).get();
    }

    @Override
    public Long addCake(CakeDetail cake){
        CakeEntity cakeEntity = new CakeEntity();
        cakeEntity.setCalories(cake.getCalories());
        cakeEntity.setImage(cake.getImage());
        cakeEntity.setDescription(cake.getDescription());
        cakeEntity.setName(cake.getName());
        cakeEntity.setPrice(cake.getPrice());
        cakeEntity.setWeight(cake.getWeight());
        cakeEntity.setShelfLife(cake.getShelfLife());
        cakeRepository.save(cakeEntity);
        return cakeEntity.getId();
    }

    @Override
    public void deleteCake(Long id) {
        CakeEntity cake = cakeRepository.getById(id);
        cakeRepository.delete(cake);
    }
}
