package com.poly.truongnvph29176.service.impl;

import com.poly.truongnvph29176.dto.request.OrderRequest;
import com.poly.truongnvph29176.entity.Account;
import com.poly.truongnvph29176.entity.Order;
import com.poly.truongnvph29176.exception.DataNotFoundException;
import com.poly.truongnvph29176.model.OrderStatus;
import com.poly.truongnvph29176.repository.AccountRepository;
import com.poly.truongnvph29176.repository.OrderRepository;
import com.poly.truongnvph29176.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;

    @Override
    public Order createOrder(OrderRequest orderRequest) throws Exception {
        Account existingAccount = accountRepository.findById(orderRequest.getAccountId()).orElseThrow(() ->
                    new DataNotFoundException("Cannot find Account with id = " + orderRequest.getAccountId())
                );
        Order order = Order.builder()
                .account(existingAccount)
                .fullName(orderRequest.getFullName())
                .phoneNumber(orderRequest.getPhoneNumber())
                .address(orderRequest.getAddress())
                .orderDate(LocalDateTime.now())
                .totalMoney(orderRequest.getTotalMoney())
                .status(OrderStatus.PENDING)
                .isActive(true)
                .paymentMethod(orderRequest.getPaymentMethod())
                .build();
        return orderRepository.save(order);
    }

    @Override
    public Order getOrderById(Integer id) throws Exception {
        return orderRepository.findById(id).orElseThrow(() ->
                    new DataNotFoundException("Cannot find Order with id = " + id)
                );
    }

    @Override
    public Order updateOrder(Integer id, OrderRequest orderRequest) throws Exception {
        Order existingOrder = orderRepository.findById(id).orElseThrow(() ->
                    new DataNotFoundException("Cannot find Order with id = " + id)
                );
        Account existingAccount = accountRepository.findById(orderRequest.getAccountId()).orElseThrow(() ->
                new DataNotFoundException("Cannot find Account with id = " + orderRequest.getAccountId())
        );
        modelMapper.typeMap(OrderRequest.class, Order.class)
                .addMappings(mapper -> mapper.skip(Order :: setId));
        modelMapper.map(orderRequest, existingOrder);
        existingOrder.setAccount(existingAccount);
        return orderRepository.save(existingOrder);
    }

    @Override
    public void deleteOrder(Integer id) {
        Order order = orderRepository.findById(id).orElse(null);
        if(order != null) {
            order.setIsActive(false);
            orderRepository.save(order);
        }
    }

    @Override
    public List<Order> findOrderByAccount(Integer accountId) {
        return orderRepository.findOrderByAccountId(accountId);
    }
}
