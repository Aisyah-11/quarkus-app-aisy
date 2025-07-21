package org.aisys.client;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import org.aisys.dto.ProductStockData;
import org.aisys.dto.ProductStockUpdateRequest;
import org.aisys.entity.Product;
import org.aisys.entity.ProductStock;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/product-stocks")
@RegisterRestClient(configKey = "product-stocks-api")
public interface ProductStockClient {

    @GET
    @Path("/{code}")
    Uni<ProductStock> getProductStock(@PathParam("code") String code);

//    @PUT
//    @Path("/{code}")
//    Uni<Response> updateProductStock(@PathParam("code") String code, ProductStockUpdateRequest request);

}
