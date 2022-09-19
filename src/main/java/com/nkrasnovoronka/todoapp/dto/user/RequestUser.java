package com.nkrasnovoronka.todoapp.dto.user;

import com.nkrasnovoronka.todoapp.anotations.PasswordValueMatch;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@PasswordValueMatch(field = "password",
    fieldMatch = "confirmPassword",
    message = "Password do not match")

public record RequestUser(
    @Size(min = 2, max = 50, message = "invalid firstName length (between 2 and 50)")
    String firstName,
    @Size(min = 2, max = 50, message = "invalid lastName length (between 2 and 50)")
    String lastName,

    String password,

    String confirmPassword,
    @Email
    String email) {

}
