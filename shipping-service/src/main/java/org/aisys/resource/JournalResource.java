package org.aisys.resource;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.aisys.client.CustomerClient;
import org.aisys.client.CustomerEmailClient;
import org.aisys.client.ProductClient;
import org.aisys.client.ProductStockClient;
import org.aisys.constant.JournalStatus;
import org.aisys.dto.JournalData;
import org.aisys.dto.OrderProductRequest;
import org.aisys.entity.Journal;
import org.aisys.entity.Product;
import org.aisys.entity.User;
import org.aisys.repository.JournalRepository;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Path("order")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class JournalResource {

    @Inject
    @RestClient
    ProductClient productClient;

    @Inject
    @RestClient
    ProductStockClient productStockClient;

    @Inject
    @RestClient
    CustomerClient customerClient;

    @Inject
    @RestClient
    CustomerEmailClient customerEmailClient;

    @Inject
    JournalRepository journalRepository;

    @Inject
    JsonWebToken jwt;

    @GET
    @Path("/list")
    public Uni<List<JournalData>> getJournals() {
        String userEmail = jwt.getClaim("upn"); // Ambil email dari token JWT
        return journalRepository.find("deletedAt is null and userEmail = ?1", userEmail).list()
                .onItem()
                .transform(journals -> {
                    return journals.stream()
                            .map(journal -> {
                                JournalData journalData = new JournalData();
                                journalData.setId(journal.getId());
                                journalData.setUserId(journal.getUserId());
                                journalData.setUserName(journal.getUserName());
                                journalData.setProductName(journal.getProductName());
                                journalData.setProductId(journal.getProductId());
                                journalData.setProductPrice(journal.getProductPrice());
                                journalData.setStatus(journal.getStatus());
                                journalData.setCheckoutAt(journal.getCheckoutAt());
                                journalData.setFinishedAt(journal.getFinishedAt());

                                return journalData;
                            })
                            .toList();
                });
    }

    @GET
    @Path("/list/{userId}")
    public Uni<List<JournalData>> getJournals(@PathParam("userId") String userId) {
        return journalRepository.find("userId = ?1 and deletedAt is null", userId).list()
                .onItem()
                .transform(journals -> {
                    return journals.stream()
                            .map(journal -> {
                                JournalData journalData = new JournalData();
                                journalData.setId(journal.getId());
                                journalData.setUserId(journal.getUserId());
                                journalData.setUserName(journal.getUserName());
                                journalData.setProductName(journal.getProductName());
                                journalData.setProductId(journal.getProductId());
                                journalData.setProductPrice(journal.getProductPrice());
                                journalData.setStatus(journal.getStatus());
                                journalData.setCheckoutAt(journal.getCheckoutAt());
                                journalData.setFinishedAt(journal.getFinishedAt());

                                return journalData;
                            })
                            .toList();
                });
    }

    @POST
    @RolesAllowed({"user"})
    public Uni<Response> createOrder(OrderProductRequest request) {
        String userEmail = jwt.getClaim("upn"); //claim userEmail nya, baru lah nanti bisa dpat data dari customer service

        return Uni.combine().all()
                .unis( 
                        productClient.getProduct(request.getProductId()),
                        //productStockClient.getProductStock(request.getProductStock()),
                        customerEmailClient.getUserEmail(userEmail)
                )
                .asTuple()
                .flatMap(tuple -> {
                    //Product product = tuple.getItem1();
                    Product product = tuple.getItem1();
                    User user = tuple.getItem2();

                    if (user == null) {
                        return Uni.createFrom().item(Response.status(Response.Status.NOT_FOUND)
                                .entity("User not found " + userEmail).build());

                    }

                    Journal journal = new Journal();
                    journal.setUserId(user.getId());
                    journal.setUserEmail(userEmail);
                    journal.setProductId(product.getId());
                    journal.setProductName(product.getItem());
                    journal.setProductPrice(product.getPrice());
                    journal.setStatus(JournalStatus.PROGRESS);
                    journal.setUserName(user.getName());

                    return Panache.withTransaction(() ->
                            journalRepository.persist(journal)
                                    .onItem()
                                    .transform(__ -> Response.status(Response.Status.CREATED).build()));
                })
                .onItem()
                .ifNull()
                .continueWith(Response.status(Response.Status.NOT_FOUND).build());
    }

    @DELETE
    @Path("{id}")
    @RolesAllowed({"admin", "user"})
    public Uni<Response> deleteJournal(@PathParam("id") String id) {
        return journalRepository.find("id = ?1 and deletedAt is null", id).firstResult()
                .onItem()
                .ifNotNull()
                .transformToUni(product -> {
                    return journalRepository.update("deletedAt = ?1 where id = ?2", LocalDateTime.now(), product.getId())
                            .onItem()
                            .transform(__ -> Response.status(Response.Status.NO_CONTENT).build());
                })
                .onItem()
                .ifNull()
                .continueWith(() -> Response.status(Response.Status.NOT_FOUND).build());
    }
}
