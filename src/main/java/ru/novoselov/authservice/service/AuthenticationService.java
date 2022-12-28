package ru.novoselov.authservice.service;

import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.novoselov.authservice.data.entity.User;
import ru.novoselov.authservice.data.repository.UserRepository;
import ru.novoselov.authservice.model.request.AuthenticationRequest;
import ru.novoselov.authservice.model.response.AuthenticationResponse;
import ru.novoselov.authservice.security.JwtTokenProvider;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    public AuthenticationResponse authenticate(@NotNull AuthenticationRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new NotFoundException("User with username " + request.getUsername() + " not found"));

        return AuthenticationResponse.builder()
                .accessToken(jwtTokenProvider.accessToken(user))
                .refreshToken(jwtTokenProvider.refreshToken(user))
                .build();
    }

}
