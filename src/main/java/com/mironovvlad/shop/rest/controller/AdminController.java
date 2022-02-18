package com.mironovvlad.shop.rest.controller;

import com.mironovvlad.shop.goods.CakesService;
import com.mironovvlad.shop.orders.OrderService;
import com.mironovvlad.shop.rest.dto.CakeDetail;
import com.mironovvlad.shop.rest.dto.OrderChange;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final OrderService orderService;
    private final CakesService cakesService;

    public AdminController(OrderService orderService, CakesService cakesService) {
        this.orderService = orderService;
        this.cakesService = cakesService;
    }

    @GetMapping(value = "/panel")
    public ModelAndView getStart(){
        return new ModelAndView("panel");
    }

    @GetMapping(value = "/cakes")
    public ModelAndView getCakes(){
        ModelAndView m = new ModelAndView("cakes");
        m.addObject("cakes", cakesService.getCakes().getCakeList());
        return m;
    }

    @GetMapping(value = "/cake/add")
    public ModelAndView getCakeForm(){
        ModelAndView m = new ModelAndView("cakeAdd");
        m.addObject("cake", new CakeDetail());
        return m;
    }

    @PostMapping(value = "/cake/add")
    public RedirectView addCake(CakeDetail cake){
        Long id = cakesService.addCake(cake);
        return new RedirectView("/admin/cake/"+id.toString());
    }

    @GetMapping(value = "cake/{id}")
    public ModelAndView getCakeById(@PathVariable Long id) {
        ModelAndView m = new ModelAndView("cake");
        m.addObject("cake",cakesService.getCake(id));
        return m;
    }

    @GetMapping(value = "/cake/delete/{id}")
    public RedirectView deleteCake(@PathVariable Long id){
        cakesService.deleteCake(id);
        return new RedirectView("/admin/cakes");
    }

    @GetMapping(value = "/orders")
    public ModelAndView getOrders(){
        ModelAndView maw = new ModelAndView("orders");
        maw.addObject("orders", orderService.getOrders());
        return maw;
    }

    @GetMapping(value = "/order/{id}")
    public ModelAndView getOrder(@PathVariable Long id){
        ModelAndView maw = new ModelAndView("order");
        maw.addObject("status", new OrderChange() );
        maw.addObject("order", orderService.getOrder(id));
        return maw;
    }

    @PostMapping(value = "/order/change/{id}")
    public RedirectView changeOrder(@PathVariable Long id, OrderChange changeInOrder){
        orderService.changeOrderStatus(id,changeInOrder.getStatus());
        return new RedirectView("/admin/order/{id}");
    }

    @GetMapping(value = "/order/delete/{id}")
    public RedirectView deleteOrder(@PathVariable Long id){
        orderService.deleteOrder(id);
        return new RedirectView("/admin/orders");
    }
}
