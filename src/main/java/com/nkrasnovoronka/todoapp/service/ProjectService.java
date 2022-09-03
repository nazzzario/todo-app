package com.nkrasnovoronka.todoapp.service;

import com.nkrasnovoronka.todoapp.dto.project.RequestProject;
import com.nkrasnovoronka.todoapp.dto.project.ResponseProject;
import com.nkrasnovoronka.todoapp.dto.user.ResponseUser;
import java.util.List;

public interface ProjectService {
  ResponseProject createProject(RequestProject requestProject, Long id);

  void addUserToProject(Long projectId, Long userId);

  void deleteProjectById(Long projectId);

  List<ResponseUser> getAllProjectUsers(Long projectId);
}
