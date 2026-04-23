package com.amanefer.orderservice.order.repository;

import com.amanefer.orderservice.order.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

    boolean existsByUserIdAndId(Long userId, Long orderId);

    void deleteByUserIdAndId(Long userId, Long orderId);
}
