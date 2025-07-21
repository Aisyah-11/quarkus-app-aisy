package org.aisys.client;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.aisys.dto.UserData;
import org.aisys.entity.User;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("auth")
@RegisterRestClient(configKey="customer-api")
public interface CustomerClient {
    @GET
    @Path("/{id}")
    Uni<User> getUser(@PathParam("id") String id);
}
