package com.nkrasnovoronka.todoapp.dto.auth;

import java.util.List;
import lombok.RequiredArgsConstructor;


public record JwtResponse(Long id, String token, String refreshToken, String email, List<String> roles) {
}
