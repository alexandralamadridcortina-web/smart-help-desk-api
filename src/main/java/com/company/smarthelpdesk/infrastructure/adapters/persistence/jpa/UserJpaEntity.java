package com.company.smarthelpdesk.infrastructure.adapters.persistence.jpa;

import com.company.smarthelpdesk.domain.model.user.RoleName;

import jakarta.persistence.*;

import java.util.HashSet;

import java.util.Set;

import java.util.UUID;

@Entity

@Table(name = "users")

public class UserJpaEntity {

    @Id

    private UUID id;

    @Column(nullable = false)

    private String fullName;

    @Column(nullable = false, unique = true)

    private String email;

    @ElementCollection(fetch = FetchType.EAGER)

    @CollectionTable(

            name = "user_roles",

            joinColumns = @JoinColumn(name = "user_id")

    )

    @Enumerated(EnumType.STRING)

    @Column(name = "role_name", nullable = false)

    private Set<RoleName> roles = new HashSet<>();

    @Column(nullable = false)

    private boolean active;

    protected UserJpaEntity() {

    }

    public UserJpaEntity(

            UUID id,

            String fullName,

            String email,

            Set<RoleName> roles,

            boolean active

    ) {
        this.id = id;

        this.fullName = fullName;

        this.email = email;

        this.roles = new HashSet<>(roles);

        this.active = active;

    }

    public UUID getId() {

        return id;

    }

    public String getFullName() {

        return fullName;

    }

    public String getEmail() {

        return email;

    }

    public Set<RoleName> getRoles() {

        return Set.copyOf(roles);

    }

    public boolean isActive() {

        return active;

    }

}
