package com.nkrasnovoronka.todoapp.dto.auth;

import javax.validation.constraints.NotBlank;

public record RequestRefreshToken(
    @NotBlank
    String refreshToken) {
}
