package com.nkrasnovoronka.todoapp.dto.project;

import com.nkrasnovoronka.todoapp.model.AppUser;

public record ResponseProject(Long id, String projectName, AppUser owner) {
}
