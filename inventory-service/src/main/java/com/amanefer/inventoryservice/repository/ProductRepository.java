package com.amanefer.inventoryservice.repository;

import com.amanefer.inventoryservice.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
