package com.nkrasnovoronka.todoapp.dto.auth;

import java.util.List;
import lombok.Data;
import lombok.RequiredArgsConstructor;


@Data
@RequiredArgsConstructor
public class JwtResponse {
  private final String token;
  private String type = "Bearer";
  private final String refreshToken;
  private final Long id;
  private final String email;
  private final List<String> roles;
}
