package ru.novoselov.authservice.data.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Roles {

    ROLE_ADMIN(1),
    ROLE_USER(2),
    ROLE_MODERATOR(3);

    private final int id;
}
