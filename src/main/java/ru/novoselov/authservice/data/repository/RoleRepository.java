package ru.novoselov.authservice.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.novoselov.authservice.data.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    boolean existsByName(String name);
}
