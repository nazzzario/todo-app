package com.nkrasnovoronka.todoapp.dto.auth;

import java.util.Collection;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;


@Data
@RequiredArgsConstructor
public class JwtResponse {
  private final String token;
  private String type = "Bearer";
  private final String refreshToken;
  private final Long id;
  private final String email;
  private final Collection<? extends GrantedAuthority> roles;
}
