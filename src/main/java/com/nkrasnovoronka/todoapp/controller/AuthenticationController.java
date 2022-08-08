package com.nkrasnovoronka.todoapp.controller;


import com.nkrasnovoronka.todoapp.dto.auth.JwtResponse;
import com.nkrasnovoronka.todoapp.dto.auth.RefreshTokenRequest;
import com.nkrasnovoronka.todoapp.dto.auth.RefreshTokenResponse;
import com.nkrasnovoronka.todoapp.dto.user.AuthenticationRequest;
import com.nkrasnovoronka.todoapp.exception.RefreshTokenException;
import com.nkrasnovoronka.todoapp.mapper.UserMapper;
import com.nkrasnovoronka.todoapp.model.RefreshToken;
import com.nkrasnovoronka.todoapp.security.AppUserDetailsImpl;
import com.nkrasnovoronka.todoapp.security.jwt.JwtUtils;
import com.nkrasnovoronka.todoapp.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
  private final UserMapper userMapper;
  private final JwtUtils jwtUtils;
  private final AuthenticationManager authenticationManager;

  private final RefreshTokenService refreshTokenService;

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody AuthenticationRequest authRequest) {
    Authentication authenticate = authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(authRequest.email(), authRequest.password()));
    SecurityContextHolder.getContext().setAuthentication(authenticate);
    var userDetails = (AppUserDetailsImpl) authenticate.getPrincipal();
    RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());
    String jwtToken = jwtUtils.generateJwtToken(userDetails);

    return ResponseEntity.ok(new JwtResponse(jwtToken, refreshToken.getToken(), userDetails.getId(),
        userDetails.getUsername(), userDetails.getAuthorities()));

  }

  @PostMapping("/logout")
  public ResponseEntity<String> logout(@RequestBody Long user_id) {
    refreshTokenService.deleteByUserId(user_id);
    return ResponseEntity.ok().body("Logout successful");
  }

  @PostMapping("/refresh")
  public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
    String refreshToken = refreshTokenRequest.refreshToken();
    return refreshTokenService.findByToken(refreshToken)
        .map(refreshTokenService::verifyExpiration)
        .map(RefreshToken::getUser)
        .map(user -> {
          String token = jwtUtils.generateTokenFromEmail(user.getEmail());
          return ResponseEntity.ok(new RefreshTokenResponse(token, refreshToken));
        })
        .orElseThrow(() -> new RefreshTokenException(refreshToken,
            "Refresh token is not in database!"));
  }
}
