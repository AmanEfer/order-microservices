package com.amanefer.orderservice.order.service;

import com.amanefer.orderservice.exception.BadRequestException;
import com.amanefer.orderservice.exception.OrderNotFoundException;
import com.amanefer.orderservice.inventory.client.InventoryClient;
import com.amanefer.orderservice.inventory.grpc.ProductResponse;
import com.amanefer.orderservice.mapper.OrderMapper;
import com.amanefer.orderservice.order.model.dto.CreateOrderRequest;
import com.amanefer.orderservice.order.model.dto.OrderItemRequest;
import com.amanefer.orderservice.order.model.dto.OrderResponse;
import com.amanefer.orderservice.order.model.entity.Order;
import com.amanefer.orderservice.order.model.entity.OrderItem;
import com.amanefer.orderservice.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;
    private final OrderMapper orderMapper;

    public List<OrderResponse> getUserOrders(Long userId) {
        List<Order> orders = orderRepository.findAllByUserId(userId);

        return orderMapper.toOrderResponseList(orders);
    }

    public OrderResponse createOrder(Long userId, CreateOrderRequest request) {
        var items = new ArrayList<OrderItem>();
        var orderTotalPrice = BigDecimal.ZERO;

        for (OrderItemRequest item : request.items()) {
            ProductResponse product = inventoryClient.chackAvailability(item.productId(), item.quantity());

            if (!product.getAvailable()) {
                throw new BadRequestException("Продукт с ID %s недоступен".formatted(item.productId()));
            }

            var orderItem = buildOrderItem(product, item);

            items.add(orderItem);

            orderTotalPrice = orderTotalPrice.add(orderItem.getTotalPrice());
        }

        var order = Order.builder()
                .userId(userId)
                .totalPrice(orderTotalPrice)
                .items(items)
                .build();

        order = orderRepository.save(order);

        return orderMapper.toOrderResponse(order);
    }

    @Transactional
    public String deleteOrderById(Long userId, Long orderId) {
        if (!orderRepository.existsByUserIdAndId(userId, orderId)) {
            throw new OrderNotFoundException("Заказ не найден");
        }

        orderRepository.deleteByUserIdAndId(userId, orderId);

        return "Заказ с ID %s удален".formatted(orderId);
    }

    private OrderItem buildOrderItem(
            ProductResponse product,
            OrderItemRequest item
    ) {
        var price = BigDecimal.valueOf(product.getPrice());
        int saleValue = product.getSale();

        var sale = BigDecimal.ONE.subtract(
                BigDecimal.valueOf(saleValue)
                        .divide(BigDecimal.valueOf(100))
        );

        var priceWithSale = price.multiply(sale);

        var itemTotalPrice = priceWithSale
                .multiply(BigDecimal.valueOf(item.quantity()))
                .setScale(2, RoundingMode.HALF_UP);

        return OrderItem.builder()
                .productId(product.getProductId())
                .name(product.getName())
                .quantity(item.quantity())
                .price(price)
                .sale(saleValue)
                .priceWithSale(priceWithSale)
                .totalPrice(itemTotalPrice)
                .build();
    }
}