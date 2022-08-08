package com.nkrasnovoronka.todoapp.dto.user;

public record RequestUser(String firstName,
                          String lastName,
                          String password,
                          String email) {

}
