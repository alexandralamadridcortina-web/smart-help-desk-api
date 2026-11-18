package com.company.smarthelpdesk.infrastructure.adapters.persistence.jpa;

import com.company.smarthelpdesk.domain.model.category.Category;
import com.company.smarthelpdesk.domain.valueobject.CategoryId;
import com.company.smarthelpdesk.infrastructure.configuration.SmarthelpdeskApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ContextConfiguration(classes = SmarthelpdeskApplication.class)
@EntityScan(basePackageClasses = CategoryJpaEntity.class)
@EnableJpaRepositories(basePackageClasses = SpringDataCategoryRepository.class)
class JpaCategoryRepositoryAdapterTest {

    @Autowired
    private SpringDataCategoryRepository springDataRepository;

    @Test
    void shouldSaveAndRecoverCategory() {
        var adapter = new JpaCategoryRepositoryAdapter(
                springDataRepository
        );

        Category category = new Category(
                new CategoryId(UUID.randomUUID()),
                "Hardware"
        );
        adapter.save(category);
        Category recovered = adapter.findById(category.getId())
                .orElseThrow();
        assertEquals(category.getId(), recovered.getId());
        assertEquals("Hardware", recovered.getName());
        assertTrue(adapter.existsByNameIgnoreCase("HARDWARE"));
    }
}
