package com.nkrasnovoronka.todoapp.dto.jwt;

import java.util.List;


public record ResponseJwt(Long id, String token, String refreshToken, String email, List<String> roles) {
}
