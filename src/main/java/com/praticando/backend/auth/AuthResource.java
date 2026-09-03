package com.praticando.backend.auth;

import com.praticando.backend.auth.dto.AuthRequest;
import com.praticando.backend.auth.dto.TokenResponse;
import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    JwtService jwtService;

    @POST
    @Path("/register")
    @Transactional
    public Response register(@Valid AuthRequest request) {
        if (User.findByUsername(request.username()) != null) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("Usuário já existe").build();
        }
        User user = new User();
        user.username = request.username();
        user.passwordHash = BcryptUtil.bcryptHash(request.password());
        user.persist();
        return Response.status(Response.Status.CREATED).build();
    }

    @POST
    @Path("/login")
    public Response login(@Valid AuthRequest request) {
        User user = User.findByUsername(request.username());
        if (user == null || !BcryptUtil.matches(request.password(), user.passwordHash)) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Credenciais inválidas").build();
        }
        TokenResponse token = jwtService.issueToken(user.username);
        return Response.ok(token).build();
    }
}