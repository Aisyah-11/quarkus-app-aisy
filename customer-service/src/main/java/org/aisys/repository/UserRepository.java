package org.aisys.repository;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.aisys.entity.User;

@ApplicationScoped
public class UserRepository implements PanacheRepositoryBase<User, String> {
}
