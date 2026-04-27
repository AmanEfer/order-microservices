package com.amanefer.inventoryservice.inventory;

import com.amanefer.inventoryservice.inventory.grpc.InventoryServiceGrpc;
import com.amanefer.inventoryservice.inventory.grpc.ReserveProductRequest;
import com.amanefer.inventoryservice.inventory.grpc.ReserveProductResponse;
import com.amanefer.inventoryservice.inventory.grpc.ReservedProduct;
import com.amanefer.inventoryservice.model.dto.ProductResponseDto;
import com.amanefer.inventoryservice.service.ProductService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.List;

@GrpcService
@RequiredArgsConstructor
public class InventoryGrpcService extends InventoryServiceGrpc.InventoryServiceImplBase {

    private final ProductService productService;

    @Override
    public void reserveProducts(
            ReserveProductRequest request,
            StreamObserver<ReserveProductResponse> responseObserver
    ) {
        ReserveProductResponse response;

        try {
            List<ProductResponseDto> productResponses = productService.reserveProducts(request.getItemsList());

            List<ReservedProduct> reservedProducts = productResponses.stream()
                    .map(this::mapToReservedProduct)
                    .toList();

            response = ReserveProductResponse.newBuilder()
                    .setSuccess(true)
                    .addAllProducts(reservedProducts)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();

        } catch (Exception e) {
            response = ReserveProductResponse.newBuilder()
                    .setSuccess(false)
                    .setErrorMessage(e.getMessage())
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }

    }

    private ReservedProduct mapToReservedProduct(ProductResponseDto product) {
        return ReservedProduct.newBuilder()
                .setProductId(product.id())
                .setName(product.name())
                .setQuantity(product.quantity())
                .setPrice(product.price().doubleValue())
                .setSale(product.sale() == null ? 0 : product.sale())
                .build();
    }
}
