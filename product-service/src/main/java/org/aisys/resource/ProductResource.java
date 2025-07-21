package org.aisys.resource;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.aisys.dto.ProductData;
import org.aisys.dto.ProductDataListStock;
import org.aisys.dto.ProductDataUpdate;
import org.aisys.dto.ProductStockData;
import org.aisys.entity.Product;
import org.aisys.entity.ProductStock;
import org.aisys.repository.ProductRepository;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Path("products")
@Produces(MediaType.APPLICATION_JSON)
public class ProductResource {

    @Inject
    ProductRepository productRepository;

    @GET
    @Path("/{id}")
    public Uni<Product> getProduct(@PathParam("id") String id) {
        return productRepository.findById(id);
    }

    @GET
    @RolesAllowed({"admin","user"})
    public Uni<List<ProductData>> getProducts() {
        return productRepository.find("deletedAt is null").list()
                .onItem()
                .transform(products -> {
                    return products.stream()
                            .map(product -> {
                                ProductData productData = new ProductData();
                                productData.setId(product.getId());
                                productData.setItem(product.getItem());
                                productData.setItem_code(product.getItem_code());
                                productData.setPrice(product.getPrice());
                                productData.setPhoto(product.getPhoto());
//                                List<ProductStock> stocks = product.getStocks();
//                                if (Objects.nonNull(stocks)) {
//                                    productData.setStocks(
//                                            stocks.stream()
//                                                    .map(stock -> {
//                                                        ProductDataListStock productDataListStock = new ProductDataListStock();
//                                                        productDataListStock.setCode(stock.getCode());
//                                                        productDataListStock.setStatus(stock.getStatus());
//
//                                                        return productDataListStock;
//                                                    }).toList()
//                                    );
//                                }
                                return productData;
                            })
                            .toList();
                });
    }
    //ambil list of product
//    @GET
//    @Path("/{id}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Uni<List<ProductData>> getProducts(@PathParam("id") String id) {
//        return productRepository.find("id = ?1 and deletedAt is null", id).list()
//                .onItem()
//                .transform(products -> {
//                    return products.stream()
//                            .map(product -> {
//                                ProductData productData = new ProductData();
//                                productData.setId(product.getId());
//                                productData.setItem(product.getItem());
//                                productData.setItem_code(product.getItem_code());
//                                productData.setDescription(product.getDescription());
//                                productData.setPhoto(product.getPhoto());
//
//                                List<ProductStock> stocks = product.getStocks();
//                                if (Objects.nonNull(stocks)) {
//                                    productData.setStocks(
//                                            stocks.stream()
//                                                    .map(stock -> {
//                                                        ProductStockData productStockData = new ProductStockData();
//                                                        productStockData.setCode(stock.getCode());
//                                                        productStockData.setStatus(stock.getStatus());
//
//                                                        return productStockData;
//                                                    }).toList()
//                                    );
//                                }
//
//                                return productData;
//                            })
//                            .toList();
//                });
//    }

    @POST
    @RolesAllowed({"admin"})
    public Uni<Response> createProduct(ProductData req) {
        Product product = new Product();
        product.setItem(req.getItem());
        product.setItem_code(req.getItem_code());
        product.setPrice(req.getPrice());
        // product.setPhoto(req.getPhoto());

        // return panache ini untuk kirim ke database
        return Panache.withTransaction(() -> productRepository.persist(product).onItem()
                .transform(__ -> {
                    return Response.status(Response.Status.CREATED).build();
                }));
    }

    @DELETE
    @Path("{id}")
    @RolesAllowed({"admin"})
    public Uni<Response> deleteProduct(@PathParam("id") String id) {
        return productRepository.find("id = ?1 and deletedAt is null", id).firstResult()
                .onItem()
                .ifNotNull()
                .transformToUni(product -> {
                    return productRepository.update("deletedAt = ?1 where id = ?2", Instant.now(), product.getId())
                            .onItem()
                            .transform(__ -> Response.status(Response.Status.NO_CONTENT).build());
                })
                .onItem()
                .ifNull()
                .continueWith(() -> Response.status(Response.Status.NOT_FOUND).build());
    }

    @PUT
    @Path("{id}")
    @RolesAllowed({"admin"})
    public Uni<Response> updateProduct(@PathParam("id") String id, ProductDataUpdate req) {
        return productRepository.find("id = ?1 and deletedAt is null", id).firstResult()
                .onItem()
                .ifNotNull()
                .transformToUni(product -> {
                    product.setItem(req.getItem());
                    product.setPrice(req.getPrice());
                    // product.setItem_code(req.getItem_code());
                    //product.setDescription(req.getDescription());
                    // product.setPhoto(req.getPhoto());

                    return Panache.withTransaction(() ->
                            productRepository.persist(product)
                                    .onItem()
                                    .transform(__ -> Response.status(Response.Status.OK).build()));
                })
                .onItem()
                .ifNull()
                .continueWith(() -> Response.status(Response.Status.NOT_FOUND).build());
    }
}
