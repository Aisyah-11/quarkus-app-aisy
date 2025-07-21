package org.aisys.resource;

import io.smallrye.jwt.build.Jwt;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.aisys.dto.LoginRequest;
import org.aisys.dto.LoginResponse;
import org.aisys.entity.User;
import org.aisys.repository.UserRepository;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;

@Path("auth")
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    UserRepository userRepository;

    @GET
    @Path("/{id}")
    public Uni<User> getUser(@PathParam("id") String id) {
        return userRepository.findById(id);
    }

    @GET
    @Path("email")
    public Uni<User> getUserEmail(@QueryParam("email") String email) {
        return userRepository.find("email", email).firstResult();
    }

    @POST
    @Path("login")
    public Uni<Response> login(LoginRequest request) {
        return userRepository.find("email = ?1 and password = ?2", request.getEmail(), request.getPassword()).firstResult()
                .onItem()
                .transform(user -> {
                    if (user != null) {

                        String token = Jwt.issuer("https://example.com/issuer")
                                .upn(user.getEmail())
                                .groups(Collections.singleton(user.getRole()))  // role tunggal, misal "Admin" atau "User"
                                .expiresAt(Instant.now().plus(1, ChronoUnit.DAYS))
                                .sign();

                        LoginResponse loginResponse = new LoginResponse();
                        loginResponse.setToken(token);
                        return Response.ok(loginResponse).build();
                    } else {
                        return Response.status(Response.Status.UNAUTHORIZED).build();
                    }
                })
                .onItem()
                .ifNull()
                .continueWith(Response.status(Response.Status.UNAUTHORIZED).build());
    }

}
