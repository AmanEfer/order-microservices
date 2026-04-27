package com.amanefer.inventoryservice.service;

import com.amanefer.inventoryservice.exceptions.ProductNotEnoughException;
import com.amanefer.inventoryservice.exceptions.ProductNotFoundException;
import com.amanefer.inventoryservice.inventory.grpc.ProductRequest;
import com.amanefer.inventoryservice.mapper.ProductMapper;
import com.amanefer.inventoryservice.model.dto.CreateProductRequest;
import com.amanefer.inventoryservice.model.dto.ProductResponseDto;
import com.amanefer.inventoryservice.model.dto.UpdateProductRequest;
import com.amanefer.inventoryservice.model.entity.Product;
import com.amanefer.inventoryservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Transactional
    public List<ProductResponseDto> reserveProducts(List<ProductRequest> productRequests) {
        var productResponses = new ArrayList<ProductResponseDto>();

        var products = productRepository.findAllById(
                productRequests.stream()
                        .map(ProductRequest::getProductId)
                        .toList()
        );

        Map<Long, Product> productsMap = products.stream()
                .collect(Collectors.toMap(Product::getId, Function.identity()));

        for (ProductRequest request : productRequests) {
            long productId = request.getProductId();
            Product product = productsMap.get(productId);

            if (product == null) {
                throw new ProductNotFoundException("Товара с ID " + productId + " нет на складе");
            }

            if (product.getQuantity().compareTo(request.getQuantity()) < 0) {
                throw new ProductNotEnoughException("На складе недостаточно товара с ID %d(%s). Попробуйте заказать меньше"
                        .formatted(productId, product.getName()));
            }

            product.setQuantity(product.getQuantity() - request.getQuantity());

            var productResponse = ProductResponseDto.builder()
                    .id(productId)
                    .name(product.getName())
                    .quantity(request.getQuantity())
                    .price(product.getPrice())
                    .sale(product.getSale())
                    .build();

            productResponses.add(productResponse);
        }

        return productResponses;
    }

    public ProductResponseDto getProductById(Long id) {
        var product = getProduct(id);

        return productMapper.toProductResponseDto(product);
    }

    public List<ProductResponseDto> getAllProducts() {
        var products = productRepository.findAll();

        return productMapper.toProductResponseDtoList(products);
    }

    @Transactional
    public ProductResponseDto addProduct(CreateProductRequest request) {
        var product = productMapper.toEntity(request);

        var savedProduct = productRepository.save(product);

        return productMapper.toProductResponseDto(savedProduct);
    }

    @Transactional
    public ProductResponseDto updateProduct(Long id, UpdateProductRequest request) {
        var product = getProduct(id);

        product.setPrice(request.price());

        var updatedProduct = productRepository.save(product);

        return productMapper.toProductResponseDto(updatedProduct);
    }

    @Transactional
    public String deleteProduct(Long id) {
        if (!productRepository.existsById(id))
            throw new RuntimeException("Продукт с ID " + id + " не найден");

        productRepository.deleteById(id);

        return "Продукт с ID " + id + " успешно удален";
    }

    private Product getProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Продукт с ID " + id + " не найден"));
    }
}
