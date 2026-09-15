package com.praticando.backend.product;

import java.util.List;

public interface ProductService {
    List<Product> topSearched();
    List<Product> search(String term);
    Product publish(String name, String description, java.math.BigDecimal price, String imageUrl);
}