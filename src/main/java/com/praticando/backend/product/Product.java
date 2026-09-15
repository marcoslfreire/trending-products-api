package com.praticando.backend.product;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "products")
public class Product extends PanacheEntity {

    @Column(nullable = false)
    public String name;

    @Column(length = 1000)
    public String description;

    @Column(nullable = false)
    public BigDecimal price;

    @Column(name = "image_url")
    public String imageUrl;

    @Column(name = "search_count", nullable = false)
    public long searchCount = 0L;

    @Column(name = "created_at", nullable = false)
    public Instant createdAt = Instant.now();
}