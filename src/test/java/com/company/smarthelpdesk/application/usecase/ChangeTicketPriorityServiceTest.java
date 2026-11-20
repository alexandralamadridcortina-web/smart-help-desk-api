package com.company.smarthelpdesk.application.usecase;

import com.company.smarthelpdesk.application.exception.ResourceNotFoundException;

import com.company.smarthelpdesk.domain.exception.InvalidValueException;

import com.company.smarthelpdesk.domain.model.ticket.Ticket;

import com.company.smarthelpdesk.domain.model.ticket.TicketPriority;

import com.company.smarthelpdesk.domain.port.out.TicketRepositoryPort;

import com.company.smarthelpdesk.domain.valueobject.CategoryId;

import com.company.smarthelpdesk.domain.valueobject.TicketId;

import com.company.smarthelpdesk.domain.valueobject.UserId;

import com.company.smarthelpdesk.infrastructure.adapters.persistence.memory.InMemoryTicketRepositoryAdapter;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ChangeTicketPriorityServiceTest {

    @Test

    void shouldChangePrioritySuccessfully() {

        TicketRepositoryPort repository =

                new InMemoryTicketRepositoryAdapter();

        ChangeTicketPriorityService service =

                new ChangeTicketPriorityService(repository);

        Ticket ticket = createTicket();

        repository.save(ticket);

        service.execute(

                ticket.getId().value(),

                TicketPriority.HIGH

        );

        Ticket updatedTicket = repository

                .findById(ticket.getId())

                .orElseThrow();

        assertEquals(

                TicketPriority.HIGH,

                updatedTicket.getPriority()

        );

    }

    @Test

    void shouldThrowExceptionWhenTicketDoesNotExist() {

        TicketRepositoryPort repository =

                new InMemoryTicketRepositoryAdapter();

        ChangeTicketPriorityService service =

                new ChangeTicketPriorityService(repository);

        UUID nonExistingId = UUID.randomUUID();

        assertThrows(

                ResourceNotFoundException.class,

                () -> service.execute(

                        nonExistingId,

                        TicketPriority.HIGH

                )

        );

    }

    @Test

    void shouldNotChangePriorityOfClosedTicket() {

        TicketRepositoryPort repository =

                new InMemoryTicketRepositoryAdapter();

        ChangeTicketPriorityService service =

                new ChangeTicketPriorityService(repository);

        Ticket ticket = createTicket();

        ticket.close();

        repository.save(ticket);

        assertThrows(

                InvalidValueException.class,

                () -> service.execute(

                        ticket.getId().value(),

                        TicketPriority.HIGH

                )

        );

        Ticket storedTicket = repository

                .findById(ticket.getId())

                .orElseThrow();

        assertEquals(

                TicketPriority.LOW,

                storedTicket.getPriority()

        );

    }

    private Ticket createTicket() {

        return Ticket.open(

                new TicketId(UUID.randomUUID()),

                new UserId(UUID.randomUUID()),

                new CategoryId(UUID.randomUUID()),

                "Problema con el computador",

                "El computador presenta fallas al iniciar",

                TicketPriority.LOW

        );

    }

}
