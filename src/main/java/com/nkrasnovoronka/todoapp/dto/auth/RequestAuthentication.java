package com.nkrasnovoronka.todoapp.dto.auth;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public record RequestAuthentication(
    @Email
    String email,
    @NotBlank
    String password) {
}
