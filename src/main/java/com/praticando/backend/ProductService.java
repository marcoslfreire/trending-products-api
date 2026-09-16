package com.praticando.backend;

import java.util.List;

public interface ProductService {
    List<Product> topSearched();

    List<Product> search(String term);

    Product publish(String name, String description, java.math.BigDecimal price, String imageUrl);

    public default List<Product> listAllProducts() {
        return listAll();
    }

    List<Product> listAll();
}