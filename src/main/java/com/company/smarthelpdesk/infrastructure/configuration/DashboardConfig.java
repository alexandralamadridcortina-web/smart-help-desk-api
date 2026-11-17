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
@Configuration
public class DashboardConfig {

    @Bean
    public TicketRepositoryPort ticketRepositoryPort() {
        return new InMemoryTicketRepositoryAdapter();
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
    public UserRepositoryPort userRepositoryPort() {
        return new InMemoryUserRepositoryAdapter();
    }
    @Bean
    public CategoryRepositoryPort categoryRepositoryPort() {
        return new InMemoryCategoryRepositoryAdapter();
    }
}
