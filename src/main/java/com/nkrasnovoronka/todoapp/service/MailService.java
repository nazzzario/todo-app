package com.nkrasnovoronka.todoapp.service;

import com.nkrasnovoronka.todoapp.model.AppUser;

public interface MailService {

  void sendVerificationCodeToUser(AppUser user);
}
