package com.mironovvlad.shop.rest.controller;

import com.mironovvlad.shop.dto.Cake;
import com.mironovvlad.shop.dto.Cakes;
import com.mironovvlad.shop.exception.CakeNotFoundException;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@Validated
//@RequestMapping("v1/cakes")
public class CakeController {

        private final Cakes cakeList = new Cakes();

        public CakeController(){
                Cake cake1 = new Cake();
                cake1.setName("Praga");
                cake1.setCalories(new BigDecimal(100));
                cake1.setWeight(new BigDecimal(100));
                cake1.setPrice(new BigDecimal(100));
                cake1.setImage("");
                cake1.setId(1L);

                Cake cake2 = new Cake();
                cake2.setName("Napoleon");
                cake2.setCalories(new BigDecimal(100));
                cake2.setWeight(new BigDecimal(100));
                cake2.setPrice(new BigDecimal(100));
                cake2.setImage("");
                cake2.setId(2L);

                List<Cake> tmp = new ArrayList<Cake>();
                tmp.add(cake1);
                tmp.add(cake2);
                cakeList.setCakeList(tmp);
        }

        @GetMapping(value = "cakes", produces = MediaType.APPLICATION_JSON_VALUE)
        public Cakes cakes(){


                return cakeList;
        }

        @GetMapping(value = "cake/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
        public Cake cake(@PathVariable Long id){

                return cakeList.getCakeList().stream().filter(c -> c.getId().equals(id))
                        .findFirst()
                        .orElseThrow(() -> new CakeNotFoundException("No such cake"));
        }

}
