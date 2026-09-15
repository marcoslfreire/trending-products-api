package com.praticando.backend.product;

import com.praticando.backend.Product;
import com.praticando.backend.ProductRepository;
import com.praticando.backend.ProductService;
import com.praticando.backend.dto.ProductRequest;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.InjectMock;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@QuarkusTest
@ExtendWith(ReadableTestWatcher.class)
class ProductServiceTest {

    @Inject
    ProductService productService;

    @InjectMock
    ProductRepository productRepository;

    @Test
    void devePublicarProdutoComDadosValidos() {
        ProductRequest request = new ProductRequest(
                "Notebook Gamer", "16GB RAM, RTX", new BigDecimal("4999.90"), "http://img/x.png");

        var response = productService.publish(
                request.name(), request.description(), request.price(), request.imageUrl());

        assertEquals("Notebook Gamer", response.name);
        verify(productRepository, times(1)).persist(any(Product.class));
    }

    @Test
    void deveIncrementarContadorAoPesquisar() {
        Product produto = new Product();
        produto.name = "Mouse sem fio";
        produto.searchCount = 5;

        when(productRepository.searchByName("mouse")).thenReturn(List.of(produto));

        var resultado = productService.search("mouse");

        assertEquals(1, resultado.size());
        assertEquals(6, produto.searchCount);
    }

    @Test
    void deveRetornarTop10MaisProcurados() {
        when(productRepository.findTopSearched(10)).thenReturn(List.of());
        var resultado = productService.topSearched();
        assertNotNull(resultado);
        verify(productRepository).findTopSearched(10);
    }
}