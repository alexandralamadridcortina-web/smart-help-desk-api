package com.company.smarthelpdesk.application.usecase;

import com.company.smarthelpdesk.application.exception.ResourceNotFoundException;
import com.company.smarthelpdesk.domain.model.ticket.Ticket;
import com.company.smarthelpdesk.domain.port.out.TicketRepositoryPort;
import com.company.smarthelpdesk.domain.valueobject.TicketId;
import java.util.Objects;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class CloseTicketService implements CloseTicketUseCase {

    private final TicketRepositoryPort ticketRepository;

    public CloseTicketService(TicketRepositoryPort ticketRepository) {
        this.ticketRepository = Objects.requireNonNull(
                ticketRepository,
                "Ticket repository is required"
        );
    }

    @Override
    public void execute(UUID ticketId) {
        Objects.requireNonNull(ticketId, "Ticket ID is required");
        Ticket ticket = ticketRepository
                .findById(new TicketId(ticketId))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Ticket not found"
                ));
        ticket.close();
        ticketRepository.save(ticket);
    }
}
