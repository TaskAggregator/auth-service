package ru.novoselov.authservice.data.principal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserPrincipal {
    private Long id;
    private String username;
    private Collection<? extends GrantedAuthority> authorities;
}
