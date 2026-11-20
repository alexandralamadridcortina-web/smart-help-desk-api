package com.company.smarthelpdesk.application.usecase;

import com.company.smarthelpdesk.application.exception.ResourceNotFoundException;
import com.company.smarthelpdesk.domain.exception.InvalidValueException;
import com.company.smarthelpdesk.domain.model.ticket.Ticket;
import com.company.smarthelpdesk.domain.model.ticket.TicketPriority;
import com.company.smarthelpdesk.domain.model.ticket.TicketStatus;
import com.company.smarthelpdesk.domain.valueobject.CategoryId;
import com.company.smarthelpdesk.domain.valueobject.TicketId;
import com.company.smarthelpdesk.domain.valueobject.UserId;
import com.company.smarthelpdesk.infrastructure.adapters.persistence.memory.InMemoryTicketRepositoryAdapter;
import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class CloseTicketServiceTest {
    @Test
    void shouldCloseTicketSuccessfully() {
        var repository = new InMemoryTicketRepositoryAdapter();
        var service = new CloseTicketService(repository);
        Ticket ticket = createTicket();
        repository.save(ticket);
        service.execute(ticket.getId().value());
        Ticket updatedTicket = repository
                .findById(ticket.getId())
                .orElseThrow();
        assertEquals(
                TicketStatus.CLOSED,
                updatedTicket.getStatus()
        );
    }

    @Test
    void shouldThrowExceptionWhenTicketDoesNotExist() {
        var repository = new InMemoryTicketRepositoryAdapter();
        var service = new CloseTicketService(repository);
        UUID nonExistingId = UUID.randomUUID();
        assertThrows(
                ResourceNotFoundException.class,
                () -> service.execute(nonExistingId)
        );
    }

    @Test
    void shouldNotCloseAnAlreadyClosedTicket() {
        var repository = new InMemoryTicketRepositoryAdapter();
        var service = new CloseTicketService(repository);
        Ticket ticket = createTicket();
        ticket.close();
        repository.save(ticket);
        assertThrows(
                InvalidValueException.class,
                () -> service.execute(ticket.getId().value())
        );
    }

    private Ticket createTicket() {
        return Ticket.open(
                new TicketId(UUID.randomUUID()),
                new UserId(UUID.randomUUID()),
                new CategoryId(UUID.randomUUID()),
                "Computador no enciende",
                "El computador no responde al encender",
                TicketPriority.HIGH
        );
    }
}
