package com.nkrasnovoronka.todoapp.dto.jwt;

import javax.validation.constraints.NotBlank;

public record RequestRefreshToken(
    @NotBlank
    String refreshToken) {
}
