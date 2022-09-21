package com.nkrasnovoronka.todoapp.service.impl;

import com.nkrasnovoronka.todoapp.dto.jwt.ResponseRefreshToken;
import com.nkrasnovoronka.todoapp.exception.RefreshTokenException;
import com.nkrasnovoronka.todoapp.model.AppUser;
import com.nkrasnovoronka.todoapp.model.RefreshToken;
import com.nkrasnovoronka.todoapp.repo.RefreshTokenRepository;
import com.nkrasnovoronka.todoapp.repo.UserRepository;
import com.nkrasnovoronka.todoapp.security.jwt.JwtUtils;
import com.nkrasnovoronka.todoapp.service.RefreshTokenService;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

  @Value("${todoapp.app.refresh-token-expiration-ms}")
  private Long refreshTokenExpirationMs;

  private final RefreshTokenRepository refreshTokenRepository;

  private final UserRepository userRepository;

  private final JwtUtils jwtUtils;

  @Override
  @Transactional
  public void deleteByUserId(Long userId) {
    AppUser appUser = userRepository.findById(userId).orElseThrow();
    refreshTokenRepository.deleteByUser(appUser);
  }

  @Override
  public RefreshToken createRefreshToken(Long userId) {
    RefreshToken token = new RefreshToken();
    AppUser appUser = userRepository.findById(userId)
        .orElseThrow(() -> new UsernameNotFoundException("Cannot find user with id " + userId));
    token.setUser(appUser);
    token.setToken(UUID.randomUUID().toString());
    token.setExpiresAt(Instant.now().plusMillis(refreshTokenExpirationMs));
    token = refreshTokenRepository.save(token);
    return token;
  }

  @Override
  public RefreshToken verifyExpiration(RefreshToken refreshToken) {
    if (refreshToken.getExpiresAt().isBefore(Instant.now())) {
      refreshTokenRepository.deleteById(refreshToken.getId());
      throw new RefreshTokenException(refreshToken.getToken(), "Refresh token was expired, pleas create new sign in token");
    }
    return refreshToken;
  }

  @Override
  public Optional<RefreshToken> findByToken(String token) {
    return refreshTokenRepository.findByToken(token);
  }

  public ResponseRefreshToken getRefreshTokenResponse(String refreshToken) {
    return findByToken(refreshToken)
        .map(this::verifyExpiration)
        .map(RefreshToken::getUser)
        .map(user -> {
          String token = jwtUtils.generateTokenFromEmail(user.getEmail());
          return new ResponseRefreshToken(token, refreshToken);
        })
        .orElseThrow(() -> new RefreshTokenException(refreshToken,
            "Refresh token is not in database!"));
  }
}
