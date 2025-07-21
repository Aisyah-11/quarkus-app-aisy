package org.aisys.client;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.aisys.dto.ProductData;
import org.aisys.entity.Product;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.springframework.web.bind.annotation.GetMapping;

@Path("/products")
@RegisterRestClient(configKey = "product-api")
public interface ProductClient {

    @GET
    @Path("/{id}")
    Uni<Product> getProduct(@PathParam("id") String id);
}

