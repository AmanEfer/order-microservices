package com.amanefer.orderservice.inventory.client;

import com.amanefer.orderservice.inventory.grpc.InventoryServiceGrpc;
import com.amanefer.orderservice.inventory.grpc.ProductRequest;
import com.amanefer.orderservice.inventory.grpc.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InventoryClient {

    private final InventoryServiceGrpc.InventoryServiceBlockingStub inventoryStub;

    public ProductResponse chackAvailability(Long productId, Integer quantity) {
        var request = ProductRequest.newBuilder()
                .setProductId(productId)
                .setQuantity(quantity)
                .build();

        return inventoryStub.checkAvailability(request);
    }
}
