package com.mironovvlad.shop.rest.controller;

import com.mironovvlad.shop.goods.CakesServiceImpl;
import com.mironovvlad.shop.rest.dto.Cake;
import com.mironovvlad.shop.rest.dto.Cakes;
import com.mironovvlad.shop.exception.CakeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@Validated
//@RequestMapping("v1/cakes")
public class CakeController {

        private final Cakes cakeList = new Cakes();
        private static long idDCounter = 0;
        private final CakesServiceImpl cakesService;

        @Autowired
        public CakeController(CakesServiceImpl cakesService) {
                List<Cake> tmp = new ArrayList<Cake>();
                cakeList.setCakeList(tmp);
                this.cakesService = cakesService;
        }

//        public CakeController(){
//                Cake cake1 = new Cake();
//                cake1.setName("Praga");
//                cake1.setCalories(new BigDecimal(100));
//                cake1.setWeight(new BigDecimal(100));
//                cake1.setPrice(new BigDecimal(100));
//                cake1.setImage("");
//                cake1.setId(1L);
//
//                Cake cake2 = new Cake();
//                cake2.setName("Napoleon");
//                cake2.setCalories(new BigDecimal(100));
//                cake2.setWeight(new BigDecimal(100));
//                cake2.setPrice(new BigDecimal(100));
//                cake2.setImage("");
//                cake2.setId(2L);
//
//                List<Cake> tmp = new ArrayList<Cake>();
//                tmp.add(cake1);
//                tmp.add(cake2);
//                cakeList.setCakeList(tmp);
//        }

        @GetMapping(value = "cakes", produces = MediaType.APPLICATION_JSON_VALUE)
        public Cakes cakes(){


                return cakesService.getCakes();
        }

        @GetMapping(value = "cake/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
        public Cake cake(@PathVariable Long id) {

                return cakeList.getCakeList().stream().filter(c -> c.getId().equals(id))
                        .findFirst()
                        .orElseThrow(() -> new CakeNotFoundException("No such cake"));
        }

        @PostMapping(path = "cakes", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<Cake> createCake(@RequestBody @Valid Cake newCake){
                newCake.setId(idDCounter);
                idDCounter++;
                cakeList.getCakeList().add(newCake);
                return new ResponseEntity<>(HttpStatus.CREATED);
        }
}
