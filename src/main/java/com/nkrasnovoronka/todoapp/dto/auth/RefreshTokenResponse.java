package com.nkrasnovoronka.todoapp.dto.auth;

public record RefreshTokenResponse(String accessToken,
                                   String refreshToken) {

}
