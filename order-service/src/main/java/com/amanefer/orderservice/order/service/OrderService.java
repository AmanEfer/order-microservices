package com.amanefer.orderservice.order.service;

import com.amanefer.orderservice.exception.BadRequestException;
import com.amanefer.orderservice.exception.EntityNotFoundException;
import com.amanefer.orderservice.order.model.dto.CreateOrderRequest;
import com.amanefer.orderservice.order.model.dto.OrderItemRequest;
import com.amanefer.orderservice.order.model.dto.OrderItemResponse;
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
import java.util.Random;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderResponse createOrder(Long userId, CreateOrderRequest request) {
        var items = new ArrayList<OrderItem>();
        var orderTotalPrice = BigDecimal.ZERO;

        for (OrderItemRequest item : request.items()) {
            //todo имитация gRPC запроса в inventory-service
            ProductInfo product = getProductInfo(item.productId());

            if (product.quantity() < item.quantity()) {
                throw new BadRequestException("На складе недостаточное количество продукта с ID " + item.productId());
            }

            int saleValue = product.sale() == null ? 0 : product.sale();
            var sale = BigDecimal.ONE.subtract(
                    BigDecimal.valueOf(saleValue).divide(BigDecimal.valueOf(100))
            );

            var priceWithSale = product.price().multiply(sale);

            var itemTotalPrice = priceWithSale
                    .multiply(BigDecimal.valueOf(item.quantity()))
                    .setScale(2, RoundingMode.HALF_UP);

            OrderItem orderItem = OrderItem.builder()
                    .productId(product.productId())
                    .quantity(item.quantity())
                    .price(product.price())
                    .sale(product.sale())
                    .priceWithSale(priceWithSale)
                    .totalPrice(itemTotalPrice)
                    .build();

            items.add(orderItem);

            orderTotalPrice = orderTotalPrice.add(itemTotalPrice);
        }

        Order order = Order.builder()
                .userId(userId)
                .totalPrice(orderTotalPrice)
                .items(items)
                .build();

        order = orderRepository.save(order);

        return toOrderResponse(order);
    }

    @Transactional
    public String deleteOrderById(Long userId, Long orderId) {
        if (!orderRepository.existsByUserIdAndId(userId, orderId)) {
            throw new EntityNotFoundException("Заказ не найден");
        }

        orderRepository.deleteByUserIdAndId(userId, orderId);

        return "Заказ с ID %s удален".formatted(orderId);
    }

    private OrderResponse toOrderResponse(Order order) {
        return OrderResponse.builder()
                .orderId(order.getId())
                .userId(order.getUserId())
                .totalPrice(order.getTotalPrice())
                .items(
                        order.getItems().stream()
                                .map(item -> OrderItemResponse.builder()
                                        .productId(item.getProductId())
                                        .quantity(item.getQuantity())
                                        .price(item.getPrice())
                                        .sale(item.getSale())
                                        .priceWithSale(item.getPriceWithSale())
                                        .totalPrice(item.getTotalPrice())
                                        .build())
                                .toList()
                )
                .build();
    }

    //todo имитация gRPC запроса в inventory-service
    private ProductInfo getProductInfo(Long productId) {
        var random = new Random();

        return new ProductInfo(
                productId,
                random.nextInt(1, 11), //кол-во товара от 1 до 10
                BigDecimal.valueOf(random.nextLong(10, 101)), // цена от 10 до 100
                random.nextInt(20) // скидка до 20%
        );
    }
}

//todo мок получаемого ответа из inventory-service
record ProductInfo(
        Long productId,
        Integer quantity,
        BigDecimal price,
        Integer sale
) {
}
