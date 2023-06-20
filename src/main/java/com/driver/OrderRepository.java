package com.driver;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@Repository
public class OrderRepository {
    private HashMap<String, Order> orders = new HashMap<>();
    private HashMap<String, List<String>> partners = new HashMap<>();
    private HashMap<String, DeliveryPartner> partnerObj = new HashMap<>();
    private HashSet<String> assignedOrders = new HashSet<>();

    public boolean addOrder(String id, Order order) {
        if(orders.containsKey(id))
            return false;
        orders.put(id, order);
        return true;
    }

    public boolean addPartner(String partnerId) {
        if(partners.containsKey(partnerId))
            return false;
        partners.put(partnerId, new ArrayList<>());
        partnerObj.put(partnerId, new DeliveryPartner(partnerId));
        return true;
    }

    public boolean addOrderPartnerPair(String orderId, String partnerId) {
        if(assignedOrders.contains(orderId) || !partners.containsKey(partnerId) || !orders.containsKey(orderId))
            return false;
        assignedOrders.add(orderId);
        partners.get(partnerId).add(orderId);
        DeliveryPartner p = partnerObj.get(partnerId);
        p.setNumberOfOrders(p.getNumberOfOrders()+1);
        return true;
    }

    public Order getOrderById(String orderId) {
        return orders.get(orderId);
    }

    public DeliveryPartner getPartnerById(String partnerId) {
        return partnerObj.get(partnerId);
    }

    public Integer getOrderCountByPartner(String partnerId) {
        if(partners.containsKey(partnerId))
            return partnerObj.get(partnerId).getNumberOfOrders();
        return -1;
    }

    public List<String> getOrdersByPartnerId(String partnerId) {
        return partners.get(partnerId);
    }

    public List<String> getAllOrders() {
        return new ArrayList<>(orders.keySet());
    }

    public Integer unassignedOrdersCount() {
        return orders.size() - assignedOrders.size();
    }
}
