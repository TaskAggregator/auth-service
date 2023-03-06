package ru.novoselov.authservice.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.novoselov.authservice.data.principal.UserPrincipal;

import java.io.IOException;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private static final String BEARER = "Bearer ";
    private final Algorithm algorithm;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            var authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith(BEARER)) {
                String token = extractToken(authorizationHeader);
                var verifier = JWT.require(algorithm).build();

                var principal = decode(verifier.verify(token));

                var auth = new UsernamePasswordAuthenticationToken(principal, principal.getUsername(), principal.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(auth);
            }

            filterChain.doFilter(request, response);

        } catch (Exception e) {
            response.sendError(HttpStatus.UNAUTHORIZED.value());
        }

    }

    private UserPrincipal decode(DecodedJWT jwt) {
        var authorities = jwt.getClaims().get("roles").asList(String.class).stream()
                .map(SimpleGrantedAuthority::new).collect(Collectors.toSet());

        var id = jwt.getClaims().get("id").asLong();

        return UserPrincipal
                .builder()
                .username(jwt.getSubject())
                .id(id)
                .authorities(authorities)
                .build();

    }

    private String extractToken(String headerValue) {
        return headerValue.substring(BEARER.length());
    }
}
