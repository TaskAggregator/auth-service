package ru.novoselov.authservice.service;

import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.ServerErrorException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.novoselov.authservice.data.entity.Role;
import ru.novoselov.authservice.data.entity.Roles;
import ru.novoselov.authservice.data.entity.User;
import ru.novoselov.authservice.data.repository.RoleRepository;
import ru.novoselov.authservice.data.repository.UserRepository;
import ru.novoselov.authservice.domain.EventFactory;
import ru.novoselov.authservice.model.mapper.UserMapper;
import ru.novoselov.authservice.model.request.AuthenticationRequest;
import ru.novoselov.authservice.model.request.SignupRequest;
import ru.novoselov.authservice.model.response.AuthenticationResponse;
import ru.novoselov.authservice.model.response.RefreshResponse;
import ru.novoselov.authservice.model.response.SignupResponse;
import ru.novoselov.authservice.security.JwtTokenProvider;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final EventPublishService eventPublishService;
    private final UserMapper userMapper;

    public AuthenticationResponse authenticate(@NotNull AuthenticationRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new NotFoundException("User with username " + request.getUsername() + " not found"));

        requirePasswordMatch(request.getPassword(), user.getPasswordHash());

        return AuthenticationResponse.builder()
                .accessToken(jwtTokenProvider.accessToken(user))
                .refreshToken(jwtTokenProvider.refreshToken(user))
                .build();
    }

    @Transactional
    public SignupResponse signup(SignupRequest request) {
        requireUniqueEmail(request.getEmail());
        requireUniqueUsername(request.getUsername());

        Role role = roleRepository.findById(Roles.ROLE_USER.getId())
                .orElseThrow(() -> new ServerErrorException("Unable to assign role to user", 500));

        var user = userMapper.toUser(request, Set.of(role));
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user = userRepository.save(user);
        eventPublishService.publish(EventFactory.buildUserSignupEvent(user));

        return userMapper.toResponse(user);
    }

    public RefreshResponse refresh(String username) {
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User " + username + "was not not found"));

        return RefreshResponse.builder()
                .accessToken(jwtTokenProvider.accessToken(user))
                .build();
    }

    private void requirePasswordMatch(String password, String passwordHash) {
        if (!passwordEncoder.matches(password, passwordHash)) {
            throw new ForbiddenException("Wrong password!");
        }
    }

    private void requireUniqueEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new BadRequestException("Provided email already exists");
        }
    }

    private void requireUniqueUsername(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new BadRequestException("User with provided username already registered");
        }
    }

}
