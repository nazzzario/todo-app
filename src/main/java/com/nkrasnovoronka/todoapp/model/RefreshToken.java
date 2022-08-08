package com.nkrasnovoronka.todoapp.model;

import java.time.Instant;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "refresh_tokens")
@Getter
@Setter
public class RefreshToken {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "token")
  private String token;

  @Column(name = "expires_at")
  private Instant expiresAt;

  @OneToOne
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private AppUser user;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof RefreshToken token1)) return false;
    return Objects.equals(id, token1.id) && Objects.equals(token, token1.token) && Objects.equals(user, token1.user);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, token, user);
  }
}
