package ru.novoselov.authservice.properties;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Valid
@NoArgsConstructor
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    @NotNull
    private String issuer;

    /**
     * in milliseconds
     */
    @Min(0)
    private Long refreshTokenTtl;

    /**
     * in millliseconds
     */
    @Min(0)
    private Long accessTokenTtl;
}
