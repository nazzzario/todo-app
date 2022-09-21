package com.nkrasnovoronka.todoapp.controller;


import com.nkrasnovoronka.todoapp.dto.auth.ResponseJwt;
import com.nkrasnovoronka.todoapp.dto.auth.RequestRefreshToken;
import com.nkrasnovoronka.todoapp.dto.auth.ResponseRefreshToken;
import com.nkrasnovoronka.todoapp.dto.user.RequestAuthentication;
import com.nkrasnovoronka.todoapp.model.RefreshToken;
import com.nkrasnovoronka.todoapp.security.AppUserDetailsImpl;
import com.nkrasnovoronka.todoapp.security.jwt.JwtUtils;
import com.nkrasnovoronka.todoapp.service.RefreshTokenService;
import javax.validation.Valid;
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
  public ResponseEntity<ResponseJwt> login(@Valid @RequestBody RequestAuthentication authRequest) {
    Authentication authenticate = authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(authRequest.email(), authRequest.password()));
    SecurityContextHolder.getContext().setAuthentication(authenticate);
    var userDetails = (AppUserDetailsImpl) authenticate.getPrincipal();
    RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());
    ResponseJwt responseJwt = jwtUtils.buildJwtResponse(userDetails, refreshToken);
    return ResponseEntity.ok(responseJwt);

  }

  @PostMapping("/logout")
  public ResponseEntity<String> logout(@RequestBody Long userId) {
    refreshTokenService.deleteByUserId(userId);
    return ResponseEntity.ok().body("Logout successful");
  }

  @PostMapping("/refresh")
  public ResponseEntity<ResponseRefreshToken> refreshToken(@Valid @RequestBody RequestRefreshToken requestRefreshToken) {
    String refreshToken = requestRefreshToken.refreshToken();
    return ResponseEntity.ok(refreshTokenService.getRefreshTokenResponse(refreshToken));
  }
}
