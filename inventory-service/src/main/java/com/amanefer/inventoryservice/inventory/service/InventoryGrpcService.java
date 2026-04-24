package com.amanefer.inventoryservice.inventory.service;

import com.amanefer.inventoryservice.inventory.grpc.InventoryServiceGrpc;
import com.amanefer.inventoryservice.inventory.grpc.ProductRequest;
import com.amanefer.inventoryservice.inventory.grpc.ProductResponse;
import com.amanefer.inventoryservice.repository.ProductRepository;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class InventoryGrpcService extends InventoryServiceGrpc.InventoryServiceImplBase {

    private final ProductRepository productRepository;

    @Override
    public void checkAvailability(
            ProductRequest request,
            StreamObserver<ProductResponse> responseObserver
    ) {
        var product = productRepository.findById(request.getProductId())
                .orElseThrow(() ->
                        new IllegalArgumentException("Продукт с ID %s не найден"
                                .formatted(request.getProductId())));

        boolean available = product.getQuantity() >= request.getQuantity();
        int sale = product.getSale() == null ? 0 : product.getSale();

        ProductResponse response = ProductResponse.newBuilder()
                .setProductId(product.getId())
                .setName(product.getName())
                .setQuantity(product.getQuantity())
                .setPrice(product.getPrice().doubleValue())
                .setSale(sale)
                .setAvailable(available)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
