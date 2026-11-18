package com.company.smarthelpdesk.infrastructure.configuration;
import com.company.smarthelpdesk.application.query.dashboard.GetDashboardSummaryUseCase;
import com.company.smarthelpdesk.domain.port.out.TicketRepositoryPort;
import com.company.smarthelpdesk.infrastructure.adapters.persistence.memory.InMemoryTicketRepositoryAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.company.smarthelpdesk.domain.port.out.UserRepositoryPort;
import com.company.smarthelpdesk.domain.port.out.CategoryRepositoryPort;
import com.company.smarthelpdesk.infrastructure.adapters.persistence.memory.InMemoryUserRepositoryAdapter;
import com.company.smarthelpdesk.infrastructure.adapters.persistence.memory.InMemoryCategoryRepositoryAdapter;
import com.company.smarthelpdesk.infrastructure.adapters.persistence.jpa.JpaUserRepositoryAdapter;
import com.company.smarthelpdesk.infrastructure.adapters.persistence.jpa.SpringDataUserRepository;
import com.company.smarthelpdesk.infrastructure.adapters.persistence.jpa.UserJpaEntity;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import com.company.smarthelpdesk.infrastructure.adapters.persistence.jpa.CategoryJpaEntity;
import com.company.smarthelpdesk.infrastructure.adapters.persistence.jpa.JpaCategoryRepositoryAdapter;
import com.company.smarthelpdesk.infrastructure.adapters.persistence.jpa.SpringDataCategoryRepository;
import com.company.smarthelpdesk.infrastructure.adapters.persistence.jpa.JpaTicketRepositoryAdapter;
import com.company.smarthelpdesk.infrastructure.adapters.persistence.jpa.SpringDataTicketRepository;
import com.company.smarthelpdesk.infrastructure.adapters.persistence.jpa.TicketJpaEntity;

@Configuration
@EntityScan(basePackageClasses = {
        UserJpaEntity.class,
        CategoryJpaEntity.class,
        TicketJpaEntity.class
})
@EnableJpaRepositories(basePackageClasses = {
        SpringDataUserRepository.class,
        SpringDataCategoryRepository.class,
        SpringDataTicketRepository.class
})
public class DashboardConfig {


    @Bean
    public TicketRepositoryPort ticketRepositoryPort(
            SpringDataTicketRepository springDataTicketRepository
    ) {
        return new JpaTicketRepositoryAdapter(
                springDataTicketRepository
        );
    }

    @Bean
    public GetDashboardSummaryUseCase getDashboardSummaryUseCase(
            TicketRepositoryPort ticketRepositoryPort
    ) {

        return new GetDashboardSummaryUseCase(
                ticketRepositoryPort
        );
    }
    @Bean
    public UserRepositoryPort userRepositoryPort(
            SpringDataUserRepository springDataUserRepository
    ) {
        return new JpaUserRepositoryAdapter(
                springDataUserRepository
        );
    }
    @Bean
    public CategoryRepositoryPort categoryRepositoryPort(
            SpringDataCategoryRepository springDataCategoryRepository
    ) {
        return new JpaCategoryRepositoryAdapter(
                springDataCategoryRepository
        );
    }
}
