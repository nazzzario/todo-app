package com.nkrasnovoronka.todoapp.service.impl;

import com.nkrasnovoronka.todoapp.model.AppUser;
import com.nkrasnovoronka.todoapp.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

  private final JavaMailSender javaMailSender;

  public void sendVerificationCodeToUser(AppUser user) {
    var message = new SimpleMailMessage();
    message.setFrom("todoapp@email.com");
    message.setTo(user.getEmail());
    message.setSubject("ToDo application verification");
    var verLing = "https://localhost:8080/api/v1/users/%s/activate/%s";
    message.setText(String.format(verLing, user.getId(), user.getActivationCode()));
    javaMailSender.send(message);
  }
}
