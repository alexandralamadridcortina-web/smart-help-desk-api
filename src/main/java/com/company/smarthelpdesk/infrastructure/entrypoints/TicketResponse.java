package com.company.smarthelpdesk.infrastructure.entrypoints;
import com.company.smarthelpdesk.domain.model.ticket.Ticket;
import com.company.smarthelpdesk.domain.model.ticket.TicketPriority;
import com.company.smarthelpdesk.domain.model.ticket.TicketStatus;
import java.util.UUID;
public record TicketResponse(
        UUID id,
        UUID requesterId,
        UUID categoryId,
        String subject,
        String description,
        TicketPriority priority,
        TicketStatus status
) {
    public static TicketResponse from(Ticket ticket) {
        return new TicketResponse(
                ticket.getId().value(),
                ticket.getRequesterId().value(),
                ticket.getCategoryId().value(),
                ticket.getSubject(),
                ticket.getDescription(),
                ticket.getPriority(),
                ticket.getStatus()
        );
    }
}