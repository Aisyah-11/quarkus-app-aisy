package org.aisys.client;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import org.aisys.entity.User;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("auth")
@RegisterRestClient(configKey="customer-api")
public interface CustomerEmailClient {
    @GET
    @Path("email")
    Uni<User> getUserEmail(@QueryParam("email") String email);
}
