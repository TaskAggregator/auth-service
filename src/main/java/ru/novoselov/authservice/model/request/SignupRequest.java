package ru.novoselov.authservice.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignupRequest {
    @Email(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    private String email;

    @Size(min = 6, max = 64)
    private String password;

    @NotNull
    @NotBlank
    @Pattern(regexp = "^(?!\\.)(?!.*\\.$)(?!.*\\.\\.)[a-z0-9_\\.]{3,24}$", message = "following characters are allowed: 'a'-'z',  '0'-'9', '_', '.'")
    private String username;
}
