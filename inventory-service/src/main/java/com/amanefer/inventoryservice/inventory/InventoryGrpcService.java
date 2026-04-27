package com.amanefer.inventoryservice.inventory;

import com.amanefer.inventoryservice.inventory.grpc.InventoryServiceGrpc;
import com.amanefer.inventoryservice.inventory.grpc.ProductRequest;
import com.amanefer.inventoryservice.inventory.grpc.ProductResponse;
import com.amanefer.inventoryservice.service.ProductService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class InventoryGrpcService extends InventoryServiceGrpc.InventoryServiceImplBase {

    private final ProductService productService;

    @Override
    public void checkAvailability(
            ProductRequest request,
            StreamObserver<ProductResponse> responseObserver
    ) {
        var product = productService.getProductById(request.getProductId());

        boolean available = product.quantity() >= request.getQuantity();
        int sale = product.sale() == null ? 0 : product.sale();

        ProductResponse response = ProductResponse.newBuilder()
                .setProductId(product.id())
                .setName(product.name())
                .setQuantity(product.quantity())
                .setPrice(product.price().doubleValue())
                .setSale(sale)
                .setAvailable(available)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
