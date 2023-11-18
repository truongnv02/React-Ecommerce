package com.poly.truongnvph29176.service;

import com.poly.truongnvph29176.dto.request.OrderRequest;
import com.poly.truongnvph29176.entity.Order;

import java.util.List;

public interface OrderService {
    Order createOrder(OrderRequest orderRequest) throws Exception;
    Order getOrderById(Integer id) throws Exception;
    Order updateOrder(Integer id, OrderRequest orderRequest) throws Exception;
    void deleteOrder(Integer id);
    List<Order> findOrderByAccount(Integer accountId);
}
