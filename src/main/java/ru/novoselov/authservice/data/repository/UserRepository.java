package ru.novoselov.authservice.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.novoselov.authservice.data.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
