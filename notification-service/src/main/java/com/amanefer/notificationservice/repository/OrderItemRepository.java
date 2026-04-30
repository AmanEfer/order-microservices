package com.amanefer.notificationservice.repository;

import com.amanefer.notificationservice.model.entity.OrderItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    @NativeQuery("""
            select id, product_id, name, quantity, price, sale, price_with_sale, line_total
            from order_items oi 
            where oi.order_id = :orderId
            """)
    List<OrderItem> findByOrderId(@Param("orderId") Long orderId);

    @Query("""
            select i 
            from Order o left join o.items i
            where o.userId = :userId
            """)
    Page<OrderItem> findAllByUserId(@Param("userId") Long userId, Pageable pageable);
}
