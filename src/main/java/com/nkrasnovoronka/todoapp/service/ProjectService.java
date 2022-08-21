package com.nkrasnovoronka.todoapp.service;

import com.nkrasnovoronka.todoapp.dto.project.RequestProject;
import com.nkrasnovoronka.todoapp.dto.project.ResponseProject;

public interface ProjectService {
  ResponseProject createProject(RequestProject requestProject, Long id);

  void addUserToProject(Long projectId, Long userId);
}
