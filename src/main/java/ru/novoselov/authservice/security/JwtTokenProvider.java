package ru.novoselov.authservice.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.novoselov.authservice.data.entity.Role;
import ru.novoselov.authservice.data.entity.User;
import ru.novoselov.authservice.properties.JwtProperties;

import java.util.Date;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private static final String ID = "id";
    private static final String ROLES = "roles";
    private final JwtProperties jwtProperties;
    private final Algorithm algorithm;

    public String accessToken(User user) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtProperties.getAccessTokenTtl()))
                .withIssuer(jwtProperties.getIssuer())
                .withClaim(ID, user.getId())
                .withClaim(ROLES, user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                .sign(algorithm);

    }

    public String refreshToken(User user) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtProperties.getRefreshTokenTtl()))
                .withIssuer(jwtProperties.getIssuer())
                .sign(algorithm);
    }
}
