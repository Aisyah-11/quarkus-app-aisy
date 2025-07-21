package org.aisys.repository;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.aisys.entity.Journal;

@ApplicationScoped
public class JournalRepository implements PanacheRepositoryBase<Journal, String> {
}
