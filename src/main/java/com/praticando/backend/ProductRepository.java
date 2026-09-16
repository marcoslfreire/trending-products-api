package com.praticando.backend;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class ProductRepository implements PanacheRepository<Product> {

    public List<Product> findTopSearched(int limit) {
        return find("ORDER BY searchCount DESC")
                .page(0, limit)
                .list();
    }

    public List<Product> searchByName(String term) {
        return find("LOWER(name) LIKE LOWER(?1) OR LOWER(description) LIKE LOWER(?1)",
                "%" + term + "%").list();
    }

    public List<Product> listAllProducts() {
        return listAll();
    }
}