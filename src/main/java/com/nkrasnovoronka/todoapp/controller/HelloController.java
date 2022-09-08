package com.nkrasnovoronka.todoapp.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/user")
public class HelloController {

  @GetMapping("/hello")
  public String sayHello() {
    String email = SecurityContextHolder.getContext().getAuthentication().getName();
    return "Hello " + email;
  }

  @GetMapping("/usr")
  @PreAuthorize("hasRole('USER')")
  public String user(){
    return "user";
  }
}
