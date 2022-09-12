package com.nkrasnovoronka.todoapp.controller;

import com.nkrasnovoronka.todoapp.dto.user.ResponseUser;
import com.nkrasnovoronka.todoapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

  private final UserService userService;

  @PostMapping("/emails/find")
  public ResponseUser getUserByEmail(@RequestBody String email) {
    return userService.getUserByEmail(email);
  }


}
