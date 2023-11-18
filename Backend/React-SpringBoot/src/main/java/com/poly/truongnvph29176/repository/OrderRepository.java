package com.poly.truongnvph29176.repository;

import com.poly.truongnvph29176.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findOrderByAccountId(Integer id);
}
