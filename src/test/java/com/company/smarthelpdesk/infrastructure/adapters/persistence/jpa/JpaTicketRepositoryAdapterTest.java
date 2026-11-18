package com.company.smarthelpdesk.infrastructure.adapters.persistence.jpa;

import com.company.smarthelpdesk.domain.model.ticket.Ticket;

import com.company.smarthelpdesk.domain.model.ticket.TicketPriority;

import com.company.smarthelpdesk.domain.model.ticket.TicketStatus;

import com.company.smarthelpdesk.domain.valueobject.CategoryId;

import com.company.smarthelpdesk.domain.valueobject.TicketId;

import com.company.smarthelpdesk.domain.valueobject.UserId;

import com.company.smarthelpdesk.infrastructure.configuration.SmarthelpdeskApplication;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import org.springframework.test.context.ContextConfiguration;

import java.time.Instant;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest

@ContextConfiguration(classes = SmarthelpdeskApplication.class)

@EntityScan(basePackageClasses = TicketJpaEntity.class)

@EnableJpaRepositories(basePackageClasses = SpringDataTicketRepository.class)

class JpaTicketRepositoryAdapterTest {

    @Autowired

    private SpringDataTicketRepository springDataRepository;

    @Test

    void shouldSaveAndRecoverTicketWithOriginalData() {

        var adapter = new JpaTicketRepositoryAdapter(

                springDataRepository

        );

        Ticket original = Ticket.open(

                new TicketId(UUID.randomUUID()),

                new UserId(UUID.randomUUID()),

                new CategoryId(UUID.randomUUID()),

                "Computador no enciende",

                "El computador no responde",

                TicketPriority.HIGH

        );

        adapter.save(original);

        Ticket recovered = adapter.findById(original.getId())

                .orElseThrow();

        assertEquals(original.getId(), recovered.getId());

        assertEquals(original.getSubject(), recovered.getSubject());

        assertEquals(original.getDescription(), recovered.getDescription());

        assertEquals(TicketPriority.HIGH, recovered.getPriority());

        assertEquals(TicketStatus.OPEN, recovered.getStatus());

        assertEquals(original.getCreatedAt(), recovered.getCreatedAt());

        assertEquals(original.getUpdatedAt(), recovered.getUpdatedAt());

        assertEquals(1, adapter.findAll().size());

    }

    @Test

    void shouldRecoverClosedTicketWithoutChangingStatusOrDates() {

        var adapter = new JpaTicketRepositoryAdapter(

                springDataRepository

        );

        Ticket original = Ticket.open(

                new TicketId(UUID.randomUUID()),

                new UserId(UUID.randomUUID()),

                new CategoryId(UUID.randomUUID()),

                "Problema de conexión",

                "No puedo conectarme a internet",

                TicketPriority.HIGH

        );

        original.close();

        adapter.save(original);

        Ticket recovered = adapter.findById(original.getId())

                .orElseThrow();

        assertEquals(TicketStatus.CLOSED, recovered.getStatus());

        assertEquals(original.getCreatedAt(), recovered.getCreatedAt());

        assertEquals(original.getUpdatedAt(), recovered.getUpdatedAt());

    }

}
