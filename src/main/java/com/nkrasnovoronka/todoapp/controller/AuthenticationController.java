package com.nkrasnovoronka.todoapp.controller;


import com.nkrasnovoronka.todoapp.dto.auth.JwtResponse;
import com.nkrasnovoronka.todoapp.dto.auth.RefreshTokenRequest;
import com.nkrasnovoronka.todoapp.dto.auth.RefreshTokenResponse;
import com.nkrasnovoronka.todoapp.dto.user.AuthenticationRequest;
import com.nkrasnovoronka.todoapp.model.RefreshToken;
import com.nkrasnovoronka.todoapp.security.AppUserDetailsImpl;
import com.nkrasnovoronka.todoapp.security.jwt.JwtUtils;
import com.nkrasnovoronka.todoapp.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
  private final JwtUtils jwtUtils;
  private final AuthenticationManager authenticationManager;

  private final RefreshTokenService refreshTokenService;

  @PostMapping("/login")
  public ResponseEntity<JwtResponse> login(@RequestBody AuthenticationRequest authRequest) {
    Authentication authenticate = authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(authRequest.email(), authRequest.password()));
    SecurityContextHolder.getContext().setAuthentication(authenticate);
    var userDetails = (AppUserDetailsImpl) authenticate.getPrincipal();
    RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());
    JwtResponse jwtResponse = jwtUtils.buildJwtResponse(userDetails, refreshToken);
    return ResponseEntity.ok(jwtResponse);

  }

  @PostMapping("/logout")
  public ResponseEntity<String> logout(@RequestBody Long userId) {
    refreshTokenService.deleteByUserId(userId);
    return ResponseEntity.ok().body("Logout successful");
  }

  @PostMapping("/refresh")
  public ResponseEntity<RefreshTokenResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
    String refreshToken = refreshTokenRequest.refreshToken();
    return ResponseEntity.ok(refreshTokenService.getRefreshTokenResponse(refreshToken));
  }
}
