package com.company.smarthelpdesk.infrastructure.adapters.persistence.jpa;
import com.company.smarthelpdesk.domain.model.user.RoleName;
import com.company.smarthelpdesk.domain.model.user.User;
import com.company.smarthelpdesk.domain.valueobject.EmailAddress;
import com.company.smarthelpdesk.domain.valueobject.FullName;
import com.company.smarthelpdesk.domain.valueobject.UserId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import com.company.smarthelpdesk.infrastructure.configuration.SmarthelpdeskApplication;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
@DataJpaTest
@ContextConfiguration(classes = SmarthelpdeskApplication.class)
@EntityScan(basePackageClasses = UserJpaEntity.class)
@EnableJpaRepositories(basePackageClasses = SpringDataUserRepository.class)
class JpaUserRepositoryAdapterTest {
    @Autowired
    private SpringDataUserRepository springDataRepository;
    @Test
    void shouldSaveAndRecoverUserWithRolesAndActiveStatus() {
        var adapter = new JpaUserRepositoryAdapter(
                springDataRepository
        );
        User original = new User(
                new UserId(UUID.randomUUID()),
                new FullName("Ana Maria Lopez"),
                new EmailAddress("ana@example.com")
        );
        original.assignRole(RoleName.USER);
        original.deactivate();
        adapter.save(original);
        User recovered = adapter.findById(original.getId())
                .orElseThrow();
        assertEquals(original.getId(), recovered.getId());
        assertEquals(original.getEmail(), recovered.getEmail());
        assertTrue(recovered.getRoles().contains(RoleName.USER));
        assertFalse(recovered.isActive());
    }
}