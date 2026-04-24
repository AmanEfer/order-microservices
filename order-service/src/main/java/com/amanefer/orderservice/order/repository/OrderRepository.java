package com.amanefer.orderservice.order.repository;

import com.amanefer.orderservice.order.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByUserId(Long userId);

    boolean existsByUserIdAndId(Long userId, Long orderId);

    void deleteByUserIdAndId(Long userId, Long orderId);
}
