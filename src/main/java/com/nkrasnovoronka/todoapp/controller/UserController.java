package com.nkrasnovoronka.todoapp.controller;

import com.nkrasnovoronka.todoapp.dto.user.ResponseUser;
import com.nkrasnovoronka.todoapp.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping
  public List<ResponseUser> getAllUsers(){
    return userService.getAllUsers();
  }
}
