package com.nkrasnovoronka.todoapp.service.impl;

import com.nkrasnovoronka.todoapp.model.AppUser;
import com.nkrasnovoronka.todoapp.repo.UserRepository;
import com.nkrasnovoronka.todoapp.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    log.info("Getting user with email {}", email);
    AppUser appUser = userRepository.findByEmail(email).orElseThrow(() -> {
      log.error("Cannot get user with email {}", email);
      throw new UsernameNotFoundException("Cannot get user with email " + email);
    });
    return new User(appUser.getEmail(), appUser.getPassword(), appUser.getRoles());
  }
}
