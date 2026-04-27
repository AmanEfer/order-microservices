package com.amanefer.inventoryservice.service;

import com.amanefer.inventoryservice.mapper.ProductMapper;
import com.amanefer.inventoryservice.model.dto.CreateProductRequest;
import com.amanefer.inventoryservice.model.dto.ProductResponseDto;
import com.amanefer.inventoryservice.model.dto.UpdateProductRequest;
import com.amanefer.inventoryservice.model.entity.Product;
import com.amanefer.inventoryservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

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
