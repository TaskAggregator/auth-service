package ru.novoselov.authservice.domain;

import lombok.experimental.UtilityClass;
import ru.novoselov.authservice.data.entity.User;
import ru.novoselov.authservice.domain.event.UserSignup;

@UtilityClass
public class EventFactory {

    public UserSignup buildUserSignupEvent(User user) {
        return new UserSignup(user);
    }
}
