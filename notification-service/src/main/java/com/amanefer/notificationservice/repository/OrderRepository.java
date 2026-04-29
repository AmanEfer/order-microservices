package com.amanefer.notificationservice.repository;

import com.amanefer.notificationservice.model.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("from Order o join fetch o.items")
    Page<Order> findAll(Pageable pageable);

    List<Order> findAllByUserId(Long userId);

    boolean existsByUserIdAndId(Long userId, Long id);
}
