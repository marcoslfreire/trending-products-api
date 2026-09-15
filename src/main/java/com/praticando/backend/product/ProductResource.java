package com.praticando.backend.product;

import com.praticando.backend.product.dto.ProductRequest;
import com.praticando.backend.product.dto.ProductResponse;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/products")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProductResource {

    @Inject
    ProductService productService;

    @GET
    @Path("/top")
    @PermitAll
    public List<ProductResponse> top() {
        return productService.topSearched().stream()
                .map(ProductResponse::from)
                .toList();
    }

    @GET
    @Path("/search")
    @PermitAll
    public List<ProductResponse> search(@QueryParam("q") String term) {
        if (term == null || term.isBlank()) {
            return List.of();
        }
        return productService.search(term).stream()
                .map(ProductResponse::from)
                .toList();
    }

    @POST
    @RolesAllowed("user")
    public Response publish(@Valid ProductRequest request) {
        Product product = productService.publish(
                request.name(), request.description(), request.price(), request.imageUrl());
        return Response.status(Response.Status.CREATED)
                .entity(ProductResponse.from(product))
                .build();
    }
}