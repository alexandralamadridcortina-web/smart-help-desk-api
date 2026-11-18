package com.company.smarthelpdesk.infrastructure.configuration;
import com.company.smarthelpdesk.domain.model.category.Category;
import com.company.smarthelpdesk.domain.port.out.CategoryRepositoryPort;
import com.company.smarthelpdesk.domain.valueobject.CategoryId;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.UUID;

@Configuration
public class CategoryDataInitializer {

    @Bean
    public CommandLineRunner initializeCategories(
            CategoryRepositoryPort categoryRepository

    ) {

        return args -> {
            if (!categoryRepository.existsByNameIgnoreCase("Hardware")) {
                Category hardware = new Category(
                        new CategoryId(UUID.randomUUID()),
                        "Hardware"
                );
                categoryRepository.save(hardware);
                System.out.println(
                        "Hardware category ID: " + hardware.getId().value()
                );
            } else {
                System.out.println(
                        "Hardware category already exists"
                );
            }
        };
    }
}
