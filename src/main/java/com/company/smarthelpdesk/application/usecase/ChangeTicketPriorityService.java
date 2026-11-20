package com.company.smarthelpdesk.application.usecase;
import com.company.smarthelpdesk.application.exception.ResourceNotFoundException;
import com.company.smarthelpdesk.domain.model.ticket.Ticket;
import com.company.smarthelpdesk.domain.model.ticket.TicketPriority;
import com.company.smarthelpdesk.domain.port.out.TicketRepositoryPort;
import com.company.smarthelpdesk.domain.valueobject.TicketId;
import org.springframework.stereotype.Service;
import java.util.Objects;
import java.util.UUID;
@Service
public class ChangeTicketPriorityService
        implements ChangeTicketPriorityUseCase {
    private final TicketRepositoryPort ticketRepository;
    public ChangeTicketPriorityService(
            TicketRepositoryPort ticketRepository
    ) {
        this.ticketRepository = Objects.requireNonNull(
                ticketRepository,
                "Ticket repository is required"
        );
    }
    @Override
    public void execute(UUID ticketId, TicketPriority newPriority) {
        Objects.requireNonNull(
                ticketId,
                "Ticket ID is required"
        );
        Ticket ticket = ticketRepository
                .findById(new TicketId(ticketId))
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Ticket not found"
                        )
                );
        ticket.changePriority(newPriority);
        ticketRepository.save(ticket);
    }
}
