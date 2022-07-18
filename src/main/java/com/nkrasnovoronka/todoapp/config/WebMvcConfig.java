package com.nkrasnovoronka.todoapp.config;

import com.nkrasnovoronka.todoapp.repo.UserRepository;
import com.nkrasnovoronka.todoapp.service.UserService;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebMvcConfig {

  private final UserService userService;
  private final UserRepository userRepository;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

    AuthenticationManagerBuilder sharedObject = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);

    sharedObject.inMemoryAuthentication().withUser(new User("admin", "admin", Collections.emptyList()))
        .withUser(new User("user", "user", Collections.emptyList()));
    httpSecurity.csrf().disable();
    httpSecurity.authorizeRequests().antMatchers("/hello").authenticated();
//
    httpSecurity.formLogin();

    return httpSecurity.build();

  }

  @Bean
  public PasswordEncoder getPasswordEncoder(){
    return NoOpPasswordEncoder.getInstance();
  }
}
