package com.nkrasnovoronka.todoapp.dto.auth;

public record ResponseRefreshToken(String accessToken,
                                   String refreshToken) {

}
