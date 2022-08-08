package com.nkrasnovoronka.todoapp.dto.user;

public record ResponseUser (Long id,
                            String firstName,
                            String lastName,
                            String email){
}
