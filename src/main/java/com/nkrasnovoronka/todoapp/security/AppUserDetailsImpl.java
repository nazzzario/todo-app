package com.nkrasnovoronka.todoapp.security;

import com.nkrasnovoronka.todoapp.model.AppUser;
import com.nkrasnovoronka.todoapp.model.Role;
import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@AllArgsConstructor
@EqualsAndHashCode
public class AppUserDetailsImpl implements UserDetails {

  private Long id;
  private String email;
  private String password;
  private List<Role> roles;

  public static AppUserDetailsImpl build(AppUser appUser) {
    return new AppUserDetailsImpl(appUser.getId(),
                                  appUser.getEmail(),
                                  appUser.getPassword(),
                                  appUser.getRoles());
  }
  public Long getId() {
    return id;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return roles;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public String toString() {
    return "AppUserDetailsImpl{" +
        "id=" + id +
        ", email='" + email + '\'' +
        ", password='" + password + '\'' +
        ", roles=" + roles +
        '}';
  }
}
