package com.nkrasnovoronka.todoapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {

  @GetMapping
  public ResponseEntity<String> sayHello(@AuthenticationPrincipal User user){
    return ResponseEntity.ok(user.getUsername());
  }

  @PostMapping
  public void postMapping(){
  }
}
