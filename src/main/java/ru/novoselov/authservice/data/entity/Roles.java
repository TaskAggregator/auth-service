package ru.novoselov.authservice.data.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Roles {

    ROLE_ADMIN(1L),
    ROLE_USER(2L),
    ROLE_MODERATOR(3L);

    private final Long id;
}
