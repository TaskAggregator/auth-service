package ru.novoselov.authservice.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum KafkaTopics {

    USER_SIGNUP("user_signup");

    private final String name;
}
