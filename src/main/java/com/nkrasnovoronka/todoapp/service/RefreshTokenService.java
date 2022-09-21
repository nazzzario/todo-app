package com.nkrasnovoronka.todoapp.service;

import com.nkrasnovoronka.todoapp.dto.jwt.ResponseRefreshToken;
import com.nkrasnovoronka.todoapp.model.RefreshToken;
import java.util.Optional;

public interface RefreshTokenService {
  void deleteByUserId(Long userId);

  RefreshToken createRefreshToken(Long userId);

  RefreshToken verifyExpiration(RefreshToken refreshToken);

  Optional<RefreshToken> findByToken(String token);

  ResponseRefreshToken getRefreshTokenResponse(String refreshToken);
}
