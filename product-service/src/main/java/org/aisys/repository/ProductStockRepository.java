package org.aisys.repository;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.aisys.entity.ProductStock;

@ApplicationScoped
public class ProductStockRepository implements PanacheRepository<ProductStock> {

    public Uni<ProductStock> findByCode(String code) {
        return find("code", code).firstResult();
    }
}
