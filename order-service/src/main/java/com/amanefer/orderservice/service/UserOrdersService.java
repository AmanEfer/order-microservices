package com.amanefer.orderservice.service;

import com.amanefer.orderservice.exception.BadRequestException;
import com.amanefer.orderservice.inventory.client.InventoryClient;
import com.amanefer.orderservice.inventory.grpc.ReserveProductResponse;
import com.amanefer.orderservice.inventory.grpc.ReservedProduct;
import com.amanefer.orderservice.kafka.message.OrderItemMessage;
import com.amanefer.orderservice.kafka.message.OrderMessage;
import com.amanefer.orderservice.kafka.producer.OrderProducer;
import com.amanefer.orderservice.model.dto.order.CreateOrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserOrdersService {

    private final InventoryClient inventoryClient;
    private final OrderProducer orderProducer;

    public OrderMessage createOrder(Long userId, CreateOrderRequest request) {
        var items = new ArrayList<OrderItemMessage>();
        var orderTotal = BigDecimal.ZERO;

        ReserveProductResponse reserveProductsResponse = inventoryClient.reserveProducts(request.items());

        if (!reserveProductsResponse.getSuccess())
            throw new BadRequestException(reserveProductsResponse.getErrorMessage());

        for (ReservedProduct reservedProduct : reserveProductsResponse.getProductsList()) {
            var orderItem = buildOrderItem(reservedProduct);

            items.add(orderItem);

            orderTotal = orderTotal.add(orderItem.lineTotal());
        }

        var event = OrderMessage.builder()
                .userId(userId)
                .orderTotal(orderTotal)
                .items(items)
                .build();

        orderProducer.sendMessage(event);

        return event;
    }

    private OrderItemMessage buildOrderItem(ReservedProduct product) {
        var price = BigDecimal.valueOf(product.getPrice());
        int saleValue = product.getSale();

        var sale = BigDecimal.ONE.subtract(
                BigDecimal.valueOf(saleValue)
                        .divide(BigDecimal.valueOf(100))
        );

        var priceWithSale = price.multiply(sale);

        var lineTotal = priceWithSale
                .multiply(BigDecimal.valueOf(product.getQuantity()))
                .setScale(2, RoundingMode.HALF_UP);

        return OrderItemMessage.builder()
                .productId(product.getProductId())
                .name(product.getName())
                .quantity(product.getQuantity())
                .price(price)
                .sale(saleValue)
                .priceWithSale(priceWithSale)
                .lineTotal(lineTotal)
                .build();
    }
}