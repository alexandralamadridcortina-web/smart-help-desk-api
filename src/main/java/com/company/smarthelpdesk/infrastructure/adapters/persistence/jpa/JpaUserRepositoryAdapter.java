package com.company.smarthelpdesk.infrastructure.adapters.persistence.jpa;

import com.company.smarthelpdesk.domain.model.user.User;
import com.company.smarthelpdesk.domain.port.out.UserRepositoryPort;
import com.company.smarthelpdesk.domain.valueobject.EmailAddress;
import com.company.smarthelpdesk.domain.valueobject.FullName;
import com.company.smarthelpdesk.domain.valueobject.UserId;
import java.util.Objects;
import java.util.Optional;

public class JpaUserRepositoryAdapter implements UserRepositoryPort {
    private final SpringDataUserRepository repository;
    public JpaUserRepositoryAdapter(
            SpringDataUserRepository repository
    ) {
        this.repository = Objects.requireNonNull(repository);
    }

    @Override
    public User save(User user) {
        UserJpaEntity entity = new UserJpaEntity(
                user.getId().value(),
                user.getFullName().value(),
                user.getEmail().value(),
                user.getRoles(),
                user.isActive()
        );
        UserJpaEntity savedEntity = repository.save(entity);
        return toDomain(savedEntity);
    }

    @Override
    public Optional<User> findById(UserId id) {
        return repository.findById(id.value())
                .map(this::toDomain);
    }

    @Override
    public Optional<User> findByEmail(EmailAddress email) {
        return repository.findByEmail(email.value())
                .map(this::toDomain);
    }

    @Override
    public boolean existsByEmail(EmailAddress email) {
        return repository.existsByEmail(email.value());
    }

    private User toDomain(UserJpaEntity entity) {
        User user = new User(
                new UserId(entity.getId()),
                new FullName(entity.getFullName()),
                new EmailAddress(entity.getEmail())
        );
        entity.getRoles().forEach(user::assignRole);
        if (!entity.isActive()) {
            user.deactivate();
        }
        return user;
    }
}
