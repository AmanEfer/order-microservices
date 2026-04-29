package com.amanefer.orderservice.inventory.client;

import com.amanefer.orderservice.inventory.grpc.InventoryServiceGrpc;
import com.amanefer.orderservice.inventory.grpc.ProductRequest;
import com.amanefer.orderservice.inventory.grpc.ReserveProductRequest;
import com.amanefer.orderservice.inventory.grpc.ReserveProductResponse;
import com.amanefer.orderservice.model.dto.order.OrderItemRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class InventoryClient {

    private final InventoryServiceGrpc.InventoryServiceBlockingStub inventoryStub;

    public ReserveProductResponse reserveProducts(List<OrderItemRequest> items) {
        var productsRequest = mapToProductRequest(items);

        var request = ReserveProductRequest.newBuilder()
                .addAllItems(productsRequest)
                .build();

        return inventoryStub.reserveProducts(request);
    }

    private List<ProductRequest> mapToProductRequest(List<OrderItemRequest> items) {
        return items.stream()
                .map(item -> ProductRequest.newBuilder()
                        .setProductId(item.productId())
                        .setQuantity(item.quantity())
                        .build())
                .toList();
    }
}
