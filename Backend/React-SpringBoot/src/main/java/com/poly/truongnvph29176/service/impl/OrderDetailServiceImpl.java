package com.poly.truongnvph29176.service.impl;

import com.poly.truongnvph29176.dto.request.OrderDetailRequest;
import com.poly.truongnvph29176.entity.Order;
import com.poly.truongnvph29176.entity.OrderDetail;
import com.poly.truongnvph29176.entity.ProductDetail;
import com.poly.truongnvph29176.exception.DataNotFoundException;
import com.poly.truongnvph29176.repository.OrderDetailRepository;
import com.poly.truongnvph29176.repository.OrderRepository;
import com.poly.truongnvph29176.repository.ProductDetailRepository;
import com.poly.truongnvph29176.service.OrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDetailServiceImpl implements OrderDetailService {
    private final OrderDetailRepository orderDetailRepository;
    private final OrderRepository orderRepository;
    private final ProductDetailRepository productDetailRepository;

    @Override
    public OrderDetail createOrderDetail(OrderDetailRequest orderDetailRequest) throws Exception {
        Order existingOrder = orderRepository.findById(orderDetailRequest.getOrderId()).orElseThrow(() ->
                    new DataNotFoundException("Cannot find Order with id = " +orderDetailRequest.getOrderId())
                );
        ProductDetail existingProductDetail = productDetailRepository
                .findById(orderDetailRequest.getProductDetailId()).orElseThrow(() ->
                                new DataNotFoundException("Cannot find productDetail with id "
                                        + orderDetailRequest.getProductDetailId())
                );
        OrderDetail orderDetail = OrderDetail.builder()
                .order(existingOrder)
                .productDetail(existingProductDetail)
                .price(orderDetailRequest.getPrice())
                .quantity(orderDetailRequest.getQuantity())
                .totalMoney(orderDetailRequest.getTotalMoney())
                .build();
        return orderDetailRepository.save(orderDetail);
    }

    @Override
    public OrderDetail findOrderDetailById(Integer id) throws Exception {
        return orderDetailRepository.findById(id).orElseThrow(() ->
                    new DataNotFoundException("Cannot find orderDetail with id = " + id)
                );
    }

    @Override
    public OrderDetail updateOrderDetail(Integer id, OrderDetailRequest orderDetailRequest) throws Exception {
        OrderDetail existingOrderDetail = findOrderDetailById(id);
        if(existingOrderDetail != null) {
            Order existingOrder = orderRepository.findById(orderDetailRequest.getOrderId()).orElseThrow(() ->
                    new DataNotFoundException("Cannot find Order with id = " +orderDetailRequest.getOrderId())
            );
            ProductDetail existingProductDetail = productDetailRepository
                    .findById(orderDetailRequest.getProductDetailId()).orElseThrow(() ->
                            new DataNotFoundException("Cannot find productDetail with id "
                                    + orderDetailRequest.getProductDetailId())
                    );
            existingOrderDetail.setOrder(existingOrder);
            existingOrderDetail.setProductDetail(existingProductDetail);
            existingOrderDetail.setPrice(orderDetailRequest.getPrice());
            existingOrderDetail.setQuantity(orderDetailRequest.getQuantity());
            existingOrderDetail.setTotalMoney(orderDetailRequest.getTotalMoney());
            return orderDetailRepository.save(existingOrderDetail);
        }
        return null;
    }

    @Override
    public void deleteOrderDetail(Integer id) {
        orderDetailRepository.deleteById(id);
    }

    @Override
    public List<OrderDetail> findByOrder(Integer orderId) {
        return orderDetailRepository.findByOrderId(orderId);
    }
}
