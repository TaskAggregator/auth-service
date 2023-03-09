package ru.novoselov.authservice.config;

import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.novoselov.authservice.properties.SecurityProperties;

@Configuration
@RequiredArgsConstructor
public class JwtAlgorithmConfig {
    private final SecurityProperties securityProperties;

    @Bean
    public Algorithm jwtAlgorithm() {
        return Algorithm.HMAC256(securityProperties.getSecret().getBytes());
    }
}
