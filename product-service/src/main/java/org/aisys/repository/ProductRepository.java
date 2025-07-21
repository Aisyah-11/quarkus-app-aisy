package org.aisys.repository;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.aisys.entity.Product;
import org.aisys.entity.ProductStock;

@ApplicationScoped
public class ProductRepository implements PanacheRepositoryBase<Product, String> {
}
