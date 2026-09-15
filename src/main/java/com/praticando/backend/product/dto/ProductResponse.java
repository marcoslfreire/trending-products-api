package com.praticando.backend.product.dto;

import com.praticando.backend.product.Product;

import java.math.BigDecimal;

public record ProductResponse(
        Long id,
        String name,
        String description,
        BigDecimal price,
        String imageUrl,
        long searchCount
) {
    public static ProductResponse from(Product p) {
        return new ProductResponse(p.id, p.name, p.description, p.price, p.imageUrl, p.searchCount);
    }
}