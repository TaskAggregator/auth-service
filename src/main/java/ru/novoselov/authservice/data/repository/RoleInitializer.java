package ru.novoselov.authservice.data.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import ru.novoselov.authservice.data.entity.Role;
import ru.novoselov.authservice.data.entity.Roles;

@Configuration
@RequiredArgsConstructor
public class RoleInitializer implements ApplicationListener<ApplicationStartedEvent> {

    private final RoleRepository roleRepository;

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        for (var role: Roles.values()) {
          if (!roleRepository.existsByName(role.name())) {
              roleRepository.save(new Role(role.getId(), role.name()));
          }
        }
    }
}
