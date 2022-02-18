package com.mironovvlad.shop.orders;

import com.mironovvlad.shop.exception.OrderNotFoundException;
import com.mironovvlad.shop.goods.CakeRepository;
import com.mironovvlad.shop.rest.dto.Order;
import com.mironovvlad.shop.rest.dto.OrderModel;
import com.mironovvlad.shop.rest.dto.PurchaseModel;
import com.mironovvlad.shop.users.UserRepository;
import com.mironovvlad.shop.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService{
    private final OrderRepository orderRepository;
    private final PurchaseRepository purchaseRepository;
    private final UserRepository userRepository;
    private final CakeRepository cakeRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, PurchaseRepository purchaseRepository, UserService userService, UserRepository userRepository, CakeRepository cakeRepository) {
        this.orderRepository = orderRepository;
        this.purchaseRepository = purchaseRepository;
        this.userRepository = userRepository;
        this.cakeRepository = cakeRepository;
    }


    @Override
    public void addOrder(Order order) {

        OrderEntity orderEntity = new OrderEntity();


        orderEntity.setDelivery(order.getDelivery());
        orderEntity.setDeliveryAddress(order.getDeliveryAddress());
        orderEntity.setDeliveryTime(order.getDeliveryTime());
        orderEntity.setPayment(order.getPayment());
        orderEntity.setStatus(order.getOrderStatus());
        orderEntity.setPurchases(order.getPurchases().stream()
                .map(purchase -> {
                    PurchaseEntity purchaseEntity = new PurchaseEntity();
                    purchaseEntity.setNumber(purchase.getNumber());
                    purchaseEntity.setOrder(orderEntity);
                    purchaseEntity.setCake(cakeRepository.findById(purchase.getId()).orElseThrow(RuntimeException::new));
                    return purchaseEntity;
                }).collect(Collectors.toList()));
        orderEntity.setUser(userRepository.findUserEntityByNumber(order.getUser().getNumber()));
        orderRepository.saveAndFlush(orderEntity);
    }

    public List<OrderModel> getOrders() {
        List<OrderEntity> orderEntities = orderRepository.findAll();
        return orderEntities.stream().map(or -> {
            OrderModel order = new OrderModel();
            order.setId(or.getId());
            order.setName(or.getUser().getName());
            order.setNumber(or.getUser().getNumber());
            order.setDelivery(or.getDelivery());
            order.setPayment(or.getPayment());
            order.setOrderStatus(or.getStatus());
            return order;
        }).collect(Collectors.toList());
    }

    @Override
    public void changeOrderStatus(Long id, OrderStatus status) {
        orderRepository.updateStatus(id,status);
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public OrderModel getOrder(Long id) {
        return orderRepository.findById(id)
                .map(orderEntity -> {
                    OrderModel order = new OrderModel();
                    order.setOrderStatus(orderEntity.getStatus());
                    order.setId(orderEntity.getId());
                    order.setName(orderEntity.getUser().getName());
                    order.setNumber(orderEntity.getUser().getNumber());
                    order.setDelivery(orderEntity.getDelivery());
                    order.setDeliveryAddress(orderEntity.getDeliveryAddress());
                    order.setDeliveryTime(orderEntity.getDeliveryTime());

                    List<PurchaseModel> purchaseModelList = orderEntity.getPurchases().stream().map(purchase -> {
                                PurchaseModel cakeInOrderInfo = cakeRepository.findById(purchase.getCake().getId()).map(
                                        cakeEntity -> {
                                            PurchaseModel purchaseModel = new PurchaseModel();
                                            purchaseModel.setName(cakeEntity.getName());
                                            purchaseModel.setPrice(cakeEntity.getPrice());
                                            return purchaseModel;
                                        }
                                ).orElse(new PurchaseModel());
                                cakeInOrderInfo.setNumber(purchase.getNumber());
                                return cakeInOrderInfo;
                            }
                    ).collect(Collectors.toList());


                    order.setPurchaseList(purchaseModelList);
                    order.setPayment(orderEntity.getPayment());
                    return order;
                })
                .orElseThrow(() -> new OrderNotFoundException("No such order"));

    }
}
