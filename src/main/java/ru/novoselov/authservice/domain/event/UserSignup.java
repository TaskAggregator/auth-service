package ru.novoselov.authservice.domain.event;

import lombok.Builder;
import lombok.Getter;
import ru.novoselov.authservice.data.entity.User;
import ru.novoselov.authservice.properties.KafkaTopics;

@Builder
@Getter
public class UserSignup extends Event {

    private User user;

    public UserSignup(User user) {
        super(KafkaTopics.USER_SIGNUP.getName());
        this.user = user;
    }
}
