package com.nkrasnovoronka.todoapp.dto.auth;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RefreshTokenResponse {
  private final String accessToken;
  private final String refreshToken;
  private String tokenType = "Bearer";
}
