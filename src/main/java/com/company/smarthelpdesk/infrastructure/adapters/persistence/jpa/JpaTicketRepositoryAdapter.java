package com.company.smarthelpdesk.infrastructure.adapters.persistence.jpa;

import com.company.smarthelpdesk.domain.model.ticket.Ticket;

import com.company.smarthelpdesk.domain.model.ticket.TicketPriority;

import com.company.smarthelpdesk.domain.model.ticket.TicketStatus;

import com.company.smarthelpdesk.domain.port.out.TicketRepositoryPort;

import com.company.smarthelpdesk.domain.valueobject.CategoryId;

import com.company.smarthelpdesk.domain.valueobject.TicketId;

import com.company.smarthelpdesk.domain.valueobject.UserId;

import java.util.List;

import java.util.Objects;

import java.util.Optional;

public class JpaTicketRepositoryAdapter

        implements TicketRepositoryPort {

    private final SpringDataTicketRepository repository;

    public JpaTicketRepositoryAdapter(

            SpringDataTicketRepository repository

    ) {

        this.repository = Objects.requireNonNull(repository);

    }

    @Override

    public Ticket save(Ticket ticket) {

        TicketJpaEntity entity = toEntity(ticket);

        TicketJpaEntity savedEntity = repository.save(entity);

        return toDomain(savedEntity);

    }

    @Override

    public Optional<Ticket> findById(TicketId id) {

        return repository.findById(id.value())

                .map(this::toDomain);

    }

    @Override

    public List<Ticket> findAll() {

        return repository.findAll()

                .stream()

                .map(this::toDomain)

                .toList();

    }

    private TicketJpaEntity toEntity(Ticket ticket) {

        return new TicketJpaEntity(

                ticket.getId().value(),

                ticket.getRequesterId().value(),

                ticket.getCategoryId().value(),

                ticket.getSubject(),

                ticket.getDescription(),

                ticket.getPriority().name(),

                ticket.getStatus().name(),

                ticket.getCreatedAt(),

                ticket.getUpdatedAt()

        );

    }

    private Ticket toDomain(TicketJpaEntity entity) {

        return Ticket.restore(

                new TicketId(entity.getId()),

                new UserId(entity.getRequesterId()),

                new CategoryId(entity.getCategoryId()),

                entity.getSubject(),

                entity.getDescription(),

                TicketPriority.valueOf(entity.getPriority()),

                TicketStatus.valueOf(entity.getStatus()),

                entity.getCreatedAt(),

                entity.getUpdatedAt()

        );

    }

}
