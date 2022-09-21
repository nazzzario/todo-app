package com.nkrasnovoronka.todoapp.dto.jwt;

public record ResponseRefreshToken(String accessToken,
                                   String refreshToken) {

}
