package com.nkrasnovoronka.todoapp.service;

import com.nkrasnovoronka.todoapp.dto.auth.RefreshTokenResponse;
import com.nkrasnovoronka.todoapp.model.RefreshToken;
import java.util.Optional;

public interface RefreshTokenService {
  void deleteByUserId(Long userId);

  RefreshToken createRefreshToken(Long userId);

  RefreshToken verifyExpiration(RefreshToken refreshToken);

  Optional<RefreshToken> findByToken(String token);

  RefreshTokenResponse getRefreshTokenResponse(String refreshToken);
}
