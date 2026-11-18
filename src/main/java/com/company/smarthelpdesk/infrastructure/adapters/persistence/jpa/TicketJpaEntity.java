package com.company.smarthelpdesk.infrastructure.adapters.persistence.jpa;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "tickets")
public class TicketJpaEntity {

    @Id
    private UUID id;

    @Column(name = "requester_id", nullable = false)
    private UUID requesterId;

    @Column(name = "category_id", nullable = false)
    private UUID categoryId;

    @Column(nullable = false)
    private String subject;

    @Column(nullable = false, length = 2000)
    private String description;

    @Column(nullable = false)
    private String priority;

    @Column(nullable = false)
    private String status;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected TicketJpaEntity() {
    }

    public TicketJpaEntity(
            UUID id,
            UUID requesterId,
            UUID categoryId,
            String subject,
            String description,
            String priority,
            String status,
            Instant createdAt,
            Instant updatedAt

    ) {
        this.id = id;
        this.requesterId = requesterId;
        this.categoryId = categoryId;
        this.subject = subject;
        this.description = description;
        this.priority = priority;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() {
        return id;
    }

    public UUID getRequesterId() {
        return requesterId;
    }

    public UUID getCategoryId() {
        return categoryId;
    }

    public String getSubject() {
        return subject;
    }

    public String getDescription() {
        return description;
    }

    public String getPriority() {
        return priority;
    }

    public String getStatus() {
        return status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
