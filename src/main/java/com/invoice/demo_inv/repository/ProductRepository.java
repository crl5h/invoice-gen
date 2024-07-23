package com.invoice.demo_inv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.invoice.demo_inv.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product findByProductName(String productName);

    Product findByProductId(String productId);

}
