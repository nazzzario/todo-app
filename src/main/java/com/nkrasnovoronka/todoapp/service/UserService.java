package com.nkrasnovoronka.todoapp.service;

import com.nkrasnovoronka.todoapp.dto.user.RequestUser;
import com.nkrasnovoronka.todoapp.dto.user.ResponseUser;
import java.util.List;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

  ResponseUser registerUser(RequestUser user);

  List<ResponseUser> getAllUsers();

  String verificationUserById(Long userId, String verificationCode);
}
