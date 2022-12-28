package ru.novoselov.authservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.novoselov.authservice.model.request.AuthenticationRequest;
import ru.novoselov.authservice.model.response.AuthenticationResponse;
import ru.novoselov.authservice.service.AuthenticationService;

@RestController
@RequestMapping("/api/auth")
public record AuthController(AuthenticationService authenticationService) {

    @GetMapping("/health")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Hello");
    }

    @PostMapping("/token")
    public ResponseEntity<AuthenticationResponse> token(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

}
