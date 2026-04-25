package com.amanefer.inventoryservice.service;

import com.amanefer.inventoryservice.model.dto.CreateProductRequest;
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

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Продукт с ID " + id + " не найден"));
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Transactional
    public Product addProduct(CreateProductRequest request) {
        var product = Product.builder()
                .name(request.name())
                .quantity(request.quantity())
                .price(request.price())
                .build();

        return productRepository.save(product);
    }

    @Transactional
    public Product updateProduct(Long id, UpdateProductRequest request) {
        var product = getProductById(id);

        product.setPrice(request.price());

        return productRepository.save(product);
    }

    @Transactional
    public String deleteProduct(Long id) {
        if (!productRepository.existsById(id))
            throw new RuntimeException("Продукт с ID " + id + " не найден");

        productRepository.deleteById(id);

        return "Продукт с ID " + id + " успешно удален";
    }
}
