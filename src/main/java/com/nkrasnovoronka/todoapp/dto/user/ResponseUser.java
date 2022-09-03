package com.nkrasnovoronka.todoapp.dto.user;

import java.util.List;

public record ResponseUser (Long id,
                            String firstName,
                            String lastName,
                            String email,
                            List<String> roles){
}
