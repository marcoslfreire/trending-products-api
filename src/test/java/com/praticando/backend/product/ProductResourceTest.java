package com.praticando.backend.product;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
@ExtendWith(ReadableTestWatcher.class)
class ProductResourceTest {

    @Test
    void deveListarTop10SemAutenticacao() {
        given()
                .when().get("/products/top")
                .then()
                .statusCode(200)
                .body("$.size()", lessThanOrEqualTo(10));
    }

    @Test
    void deveRecusarPublicacaoSemToken() {
        given()
                .contentType("application/json")
                .body("""
                        {"name":"Teclado","description":"mecanico","price":299.90}
                        """)
                .when().post("/products")
                .then()
                .statusCode(401);
    }

    @Test
    void deveBuscarComTermoVazioRetornandoListaVazia() {
        given()
                .when().get("/products/search?q=")
                .then()
                .statusCode(200)
                .body("$.size()", equalTo(0));
    }
}