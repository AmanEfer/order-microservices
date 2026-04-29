package com.amanefer.notificationservice.service;

import com.amanefer.notificationservice.kafka.message.OrderMessage;
import com.amanefer.notificationservice.mapper.OrderItemMapper;
import com.amanefer.notificationservice.mapper.OrderMapper;
import com.amanefer.notificationservice.model.dto.OrderItemPageResponse;
import com.amanefer.notificationservice.model.dto.OrderItemResponse;
import com.amanefer.notificationservice.model.dto.OrderPageResponse;
import com.amanefer.notificationservice.model.dto.OrderResponse;
import com.amanefer.notificationservice.model.entity.Order;
import com.amanefer.notificationservice.repository.OrderItemRepository;
import com.amanefer.notificationservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;
    private final OrderItemRepository orderItemRepository;

    @Transactional
    public void createNewOrder(OrderMessage event) {
        log.info("Received event with userId: {}", event.userId());

        var order = orderMapper.toEntity(event);

        orderRepository.save(order);
    }

    public OrderPageResponse getAllOrders(Pageable pageable) {
        Page<Order> orderPage = orderRepository.findAll(pageable);

        return orderMapper.toOrderPageResponse(orderPage);
    }

    public List<OrderItemResponse> getAllOrderItemsByOrderId(Long orderId) {
        var items = orderItemRepository.findByOrderId(orderId);

        return orderItemMapper.toOrderItemResponseList(items);
    }

    public OrderItemPageResponse getAllOrderItemsByUserId(Long userId, Pageable pageable) {
        var items = orderItemRepository.findAllByUserId(userId, pageable);

        return orderItemMapper.toOrderItemPageResponse(items);
    }

    public List<OrderResponse> getUserOrders(Long userId) {
        var orders = orderRepository.findAllByUserId(userId);

        return orderMapper.toOrderResponseList(orders);
    }

    @Transactional
    public String deleteOrder(Long userId, Long orderId) {
        if (orderRepository.existsByUserIdAndId(userId, orderId)) {
            orderRepository.deleteById(orderId);
            return "Заказ успешно удален";
        }

        return "Заказ с ID %d не найден".formatted(orderId);

    }
}
