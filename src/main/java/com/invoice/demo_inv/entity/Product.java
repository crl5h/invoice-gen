package com.invoice.demo_inv.entity;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "products")
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private int productId;

    @Column(name = "product_name", nullable = false, unique = true)
    private String productName;

    @Column(name = "unit_price", nullable = false)
    private Double unitPrice;

}
