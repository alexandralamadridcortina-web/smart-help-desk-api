package com.company.smarthelpdesk.infrastructure.adapters.persistence.jpa;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
public interface SpringDataCategoryRepository
        extends JpaRepository<CategoryJpaEntity, UUID> {
    boolean existsByNameIgnoreCase(String name);
}
