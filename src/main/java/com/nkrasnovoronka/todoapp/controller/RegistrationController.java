package com.nkrasnovoronka.todoapp.controller;


import com.nkrasnovoronka.todoapp.dto.user.RequestUser;
import com.nkrasnovoronka.todoapp.dto.user.ResponseUser;
import com.nkrasnovoronka.todoapp.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/registration")
@RequiredArgsConstructor
@Slf4j
public class RegistrationController {

  private final UserService userService;

  @PostMapping
  public ResponseEntity<ResponseUser> userRegistration(@RequestBody RequestUser user) {
    ResponseUser responseUser = userService.saveUser(user);
    return ResponseEntity.ok(responseUser);
  }
}
