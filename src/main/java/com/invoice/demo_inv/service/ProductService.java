package com.invoice.demo_inv.service;

import com.invoice.demo_inv.entity.Product;
import com.invoice.demo_inv.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(int id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(int id, Product productDetails) {
        Product product = productRepository.findById(id).orElse(null);
        if (product != null) {
            if (productDetails.getProductName() != null)
                product.setProductName(productDetails.getProductName());
            if (productDetails.getUnitPrice() != null)
                product.setUnitPrice(productDetails.getUnitPrice());
            return productRepository.save(product);
        }
        return null;
    }

    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    }
}
