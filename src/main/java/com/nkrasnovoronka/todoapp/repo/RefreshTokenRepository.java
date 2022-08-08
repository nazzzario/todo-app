package com.nkrasnovoronka.todoapp.repo;

import com.nkrasnovoronka.todoapp.model.AppUser;
import com.nkrasnovoronka.todoapp.model.RefreshToken;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
  Optional<RefreshToken> findByUserId(Long userId);

  Optional<RefreshToken> findByToken(String refreshToken);

  void deleteByUser(AppUser user);
}
