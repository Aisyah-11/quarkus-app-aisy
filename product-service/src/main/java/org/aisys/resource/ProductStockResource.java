package org.aisys.resource;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.aisys.constant.ProductStockStatus;
import org.aisys.dto.CreateProductStockRequest;
import org.aisys.dto.ProductStockUpdateRequest;
import org.aisys.entity.ProductStock;
import org.aisys.repository.ProductRepository;
import org.aisys.repository.ProductStockRepository;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Stream;

@RestController
@Path("product-stocks")
@Produces(MediaType.APPLICATION_JSON)
public class ProductStockResource {

    @Inject
    ProductRepository productRepository;

    @Inject
    ProductStockRepository productStockRepository;

//    @GetMapping("/{code}")
//    public ProductStockDto getProductStockData(@PathVariable("code") String code) {
//            return new ProductStockDto("productId", code, ProductStockStatus.AVAILABLE);
//    }
    @GET
    @Path("/{code}")
    public Uni<ProductStock> getProductStock(@PathParam("code") String code) {
        return productStockRepository.findByCode(code);
    }

//    @GET
//    @Path("/{code}")
//    public Uni<List<ProductStockData>> getProductStock(@PathParam("code") String code) {
//        return productStockRepository.find("code = ?1", code).list()
//                .onItem().ifNotNull()
//                .transform(productStocks -> {
//                    return productStocks.stream()
//                            .map(productStock -> {
//                                ProductStockData stock = new ProductStockData();
//                                stock.setCode(productStock.getCode());
//                                stock.setProductId(productStock.getProductId());
//                                stock.setStatus(productStock.getStatus());
//
//                                return stock;
//                            })
//                            .toList();
//                });
//    }

//    @GET
//    @Path("/{productId}")
//    public Uni<List<ProductStockData>> getProductStock(@PathParam("productId") Long productId) {
//        return productStockRepository.find("deletedAt is null").list()
//                .onItem().ifNotNull()
//                .transform(productStocks -> {
//                    return productStocks.stream()
//                            .map(productStock -> {
//                                ProductStockData stock = new ProductStockData();
//                                stock.setCode(productStock.getCode());
//                                stock.setProductId(productStock.getProductId());
//                                stock.setStatus(productStock.getStatus());
//
//                                return stock;
//                            })
//                            .toList();
//                });
//    }

    @POST
    @RolesAllowed({"admin"})
    public Uni<Response> createStock(@Valid CreateProductStockRequest request) {
        return productRepository.find("deletedAt is null and id = ?1", request.getProductId()).firstResult()
                .onItem()
                .ifNotNull()
                .transformToUni(product -> {
                    Stream<ProductStock> stocks = request.getCodes()
                            .stream()
                            .map(code -> {
                                ProductStock productStock = new ProductStock();
                                productStock.setCode(code);
                                productStock.setProductId(product.getId());
                                productStock.setProductName(product.getItem());
                                productStock.setStatus(ProductStockStatus.AVAILABLE);
                                return productStock;
                            });
                    return Panache.withTransaction(() ->
                            productStockRepository.persist(stocks)
                                    .onItem()
                                    .transform(__ -> Response.status(Response.Status.CREATED).build()));
                });
    }

    @DELETE
    @RolesAllowed({"admin"})
    public Uni<Response> deletedBook(@QueryParam("code") List<String> code) {
        return Panache.withTransaction(() ->
                productStockRepository.delete("code in ?1", code)
                        .onItem()
                        .transform(__ -> Response.status(Response.Status.NO_CONTENT).build()));

    }

    @PUT
    @Path("/{code}")
    public Uni<Response> updateStock(@PathParam("code") String code, ProductStockUpdateRequest request) {
        return productStockRepository.find("code", code).firstResult()
                .onItem().ifNotNull().transformToUni(stock -> {
                    stock.setStatus(ProductStockStatus.PROGRESS);
                    stock.setCheckoutBy(request.getCheckoutBy());
                    stock.setCheckoutAt(request.getChekoutAt());
                    return productStockRepository.persist(stock)
                            .onItem().transform(updated -> Response.ok().build());
                })
                .onItem().ifNull().continueWith(Response.status(Response.Status.NOT_FOUND).build());
    }


}
