package ru.novoselov.authservice.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.novoselov.authservice.data.principal.UserPrincipal;
import ru.novoselov.authservice.model.request.AuthenticationRequest;
import ru.novoselov.authservice.model.request.SignupRequest;
import ru.novoselov.authservice.model.response.AuthenticationResponse;
import ru.novoselov.authservice.model.response.RefreshResponse;
import ru.novoselov.authservice.model.response.SignupResponse;
import ru.novoselov.authservice.service.AuthenticationService;

@RestController
@RequestMapping("/api/auth")
public record AuthController(AuthenticationService authenticationService) {

    @PostMapping(value = "/signup",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SignupResponse> signup(@RequestBody SignupRequest request) {
        return ResponseEntity.ok(authenticationService.signup(request));
    }

    @PostMapping("/token")
    public ResponseEntity<AuthenticationResponse> token(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping("/token/refresh")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<RefreshResponse> refreshToken(@AuthenticationPrincipal UserPrincipal user) {
        return ResponseEntity.ok(authenticationService.refresh(user.getUsername()));
    }

}
