package ru.novoselov.authservice.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class RefreshResponse {
    private String accessToken;
}
