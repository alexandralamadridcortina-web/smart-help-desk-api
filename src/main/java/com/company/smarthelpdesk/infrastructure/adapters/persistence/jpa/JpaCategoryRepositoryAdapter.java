package com.company.smarthelpdesk.infrastructure.adapters.persistence.jpa;

import com.company.smarthelpdesk.domain.model.category.Category;
import com.company.smarthelpdesk.domain.port.out.CategoryRepositoryPort;
import com.company.smarthelpdesk.domain.valueobject.CategoryId;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class JpaCategoryRepositoryAdapter
        implements CategoryRepositoryPort {
    private final SpringDataCategoryRepository repository;
    public JpaCategoryRepositoryAdapter(
            SpringDataCategoryRepository repository
    ) {
        this.repository = Objects.requireNonNull(repository);
    }
    @Override
    public Category save(Category category) {
        CategoryJpaEntity entity = new CategoryJpaEntity(
                category.getId().value(),
                category.getName()
        );
        CategoryJpaEntity savedEntity = repository.save(entity);
        return toDomain(savedEntity);
    }

    @Override
    public Optional<Category> findById(CategoryId id) {
        return repository.findById(id.value())
                .map(this::toDomain);
    }

    @Override
    public List<Category> findAll() {
        return repository.findAll()
                .stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public boolean existsByNameIgnoreCase(String name) {
        return repository.existsByNameIgnoreCase(name);
    }

    private Category toDomain(CategoryJpaEntity entity) {
        return new Category(
                new CategoryId(entity.getId()),
                entity.getName()
        );
    }
}
