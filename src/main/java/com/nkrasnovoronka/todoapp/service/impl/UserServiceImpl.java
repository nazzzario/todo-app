package com.nkrasnovoronka.todoapp.service.impl;

import com.nkrasnovoronka.todoapp.dto.user.RequestUser;
import com.nkrasnovoronka.todoapp.dto.user.ResponseUser;
import com.nkrasnovoronka.todoapp.mapper.UserMapper;
import com.nkrasnovoronka.todoapp.model.AppUser;
import com.nkrasnovoronka.todoapp.model.Role;
import com.nkrasnovoronka.todoapp.model.UserStatus;
import com.nkrasnovoronka.todoapp.repo.RoleRepository;
import com.nkrasnovoronka.todoapp.repo.UserRepository;
import com.nkrasnovoronka.todoapp.security.AppUserDetailsImpl;
import com.nkrasnovoronka.todoapp.service.MailService;
import com.nkrasnovoronka.todoapp.service.UserService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final PasswordEncoder passwordEncoder;
  private final MailService mailService;
  private final UserMapper userMapper;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    log.info("Getting user with email {}", email);
    AppUser appUser = userRepository.findByEmail(email).orElseThrow(() -> {
      log.error("Cannot get user with email {}", email);
      throw new UsernameNotFoundException("Cannot get user with email " + email);
    });
    return AppUserDetailsImpl.build(appUser);
  }

  @Override
  public ResponseUser registerUser(RequestUser user) {
    Role roleUser = roleRepository.findRoleByRoleName("ROLE_USER");
    AppUser appUser = userMapper.toEntity(user);
    appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
    appUser.getRoles().add(roleUser);
    appUser.setActivationCode(UUID.randomUUID().toString());
    log.info("Saving new user {}", appUser.getEmail());
    AppUser registeredUser = userRepository.save(appUser);
    log.info("User with email {} successfully saved", registeredUser.getEmail());
    mailService.sendVerificationCodeToUser(registeredUser);
    return userMapper.toDto(registeredUser);
  }

  @Override
  public List<ResponseUser> getAllUsers() {
    return userRepository.findAll().stream()
        .map(userMapper::toDto)
        .toList();
  }

  @Override
  public String verificationUserById(Long userId, String verificationCode) {
    var userFromDb = userRepository.findById(userId)
        .orElseThrow(() -> new UsernameNotFoundException("Cannot find user with id " + userId));
    if (userFromDb.getActivationCode().equals(verificationCode)) {
      userFromDb.setUserStatus(UserStatus.ACTIVATED);
      userRepository.save(userFromDb);
      return "user activated successfully";
    }
    return "wrong activation code";
  }

  @Override
  public ResponseUser getUserByEmail(String email) {
    var userByEmail = userRepository.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("Cannot find user with email " + email));
    return userMapper.toDto(userByEmail);
  }
}
