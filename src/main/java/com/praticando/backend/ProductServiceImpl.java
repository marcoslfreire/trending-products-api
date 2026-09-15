package com.praticando.backend;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.util.List;

@ApplicationScoped
public class ProductServiceImpl implements ProductService {

    @Inject
    ProductRepository repository;

    @Override
    public List<Product> topSearched() {
        return repository.findTopSearched(10);
    }

    /**
     * Cada produto retornado na pesquisa tem o contador de popularidade incrementado.
     * É esse incremento que a automação vai validar de ponta a ponta:
     * pesquisar pela UI -> conferir que refletiu no banco.
     */
    @Override
    @Transactional
    public List<Product> search(String term) {
        List<Product> found = repository.searchByName(term);
        found.forEach(p -> p.searchCount++);
        return found;
    }

    @Override
    @Transactional
    public Product publish(String name, String description, BigDecimal price, String imageUrl) {
        Product product = new Product();
        product.name = name;
        product.description = description;
        product.price = price;
        product.imageUrl = imageUrl;
        repository.persist(product);
        return product;
    }
}