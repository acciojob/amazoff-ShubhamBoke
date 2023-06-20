package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;
    public boolean addOrder(Order order) {
        return orderRepository.addOrder(order.getId(), order);
    }

    public boolean addPartner(String partnerId) {
        return orderRepository.addPartner(partnerId);
    }

    public boolean addOrderPartnerPair(String orderId, String partnerId) {
        return orderRepository.addOrderPartnerPair(orderId, partnerId);
    }

    public Order getOrderById(String orderId) {
        return orderRepository.getOrderById(orderId);
    }

    public DeliveryPartner getPartnerById(String partnerId) {
        return orderRepository.getPartnerById(partnerId);
    }

    public Integer getOrderCountByPartner(String partnerId) {
        return orderRepository.getOrderCountByPartner(partnerId);
    }

    public List<String> getOrdersByPartnerId(String partnerId) {
        return orderRepository.getOrdersByPartnerId(partnerId);
    }

    public List<String> getAllOrders() {
        return orderRepository.getAllOrders();
    }

    public Integer unassignedOrdersCount() {
        return orderRepository.unassignedOrdersCount();
    }

    public Integer undeliveredOrders(String time, String partnerId) {
        List<String> orderList = orderRepository.getOrdersByPartnerId(partnerId);
        if(orderList == null)
            return -1;
        int count = 0;
        int t = Integer.parseInt(time.split(":")[0]) * 60 + Integer.parseInt(time.split(":")[1]);
        for(String OId: orderList){
            Order order = orderRepository.getOrderById(OId);
            if(order.getDeliveryTime() > t)
                count++;
        }
        return count;
    }

    public String getLastDeliveryTime(String partnerId) {
        List<String> orderList = orderRepository.getOrdersByPartnerId(partnerId);
        if(orderList == null) return null;
        int last = 0;
        for(String OId: orderList){
            Order order = orderRepository.getOrderById(OId);
            last = Math.max(last, order.getDeliveryTime());
        }
        String hh = Integer.toString((last - last%60)/60);
        String mm = Integer.toString(last%60);
        return hh + ":" + mm;
    }
}
