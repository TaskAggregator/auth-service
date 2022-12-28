package ru.novoselov.authservice.model.response;

public record AuthenticationResponse(String accessToken,
                                     String refreshToken) {
}
