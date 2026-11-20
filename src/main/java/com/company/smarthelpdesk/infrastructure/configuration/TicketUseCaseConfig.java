package com.company.smarthelpdesk.infrastructure.configuration;
import com.company.smarthelpdesk.application.usecase.*;
import com.company.smarthelpdesk.domain.port.out.TicketRepositoryPort;
import com.company.smarthelpdesk.domain.port.out.UserRepositoryPort;
import com.company.smarthelpdesk.domain.port.out.CategoryRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class TicketUseCaseConfig {
    @Bean
    public GetTicketByIdUseCase getTicketByIdUseCase(
            TicketRepositoryPort ticketRepository
    ) {
        return new GetTicketByIdService(ticketRepository);
    }
    @Bean
    public CreateTicketUseCase createTicketUseCase(
            TicketRepositoryPort ticketRepository,
            UserRepositoryPort userRepository,
            CategoryRepositoryPort categoryRepository
    ) {
        return new CreateTicketService(
                ticketRepository,
                userRepository,
                categoryRepository
        );
    }
    @Bean
    public CloseTicketUseCase closeTicketUseCase(
            TicketRepositoryPort ticketRepository
    ) {
        return new CloseTicketService(ticketRepository);
    }
    @Bean
    public ChangeTicketPriorityUseCase changeTicketPriorityUseCase(
            TicketRepositoryPort ticketRepository
    ) {
        return new ChangeTicketPriorityService(ticketRepository);
    }
}