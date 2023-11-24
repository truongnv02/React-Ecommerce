package com.poly.truongnvph29176.service;

import com.poly.truongnvph29176.dto.request.OrderDetailRequest;
import com.poly.truongnvph29176.entity.OrderDetail;

import java.util.List;

public interface OrderDetailService {
    OrderDetail createOrderDetail(OrderDetailRequest orderDetailRequest) throws Exception;
    OrderDetail findOrderDetailById(Integer id) throws Exception;
    OrderDetail updateOrderDetail(Integer id, OrderDetailRequest orderDetailRequest) throws Exception;
    void deleteOrderDetail(Integer id);
    List<OrderDetail> findByOrder(Integer orderId);
}
