package com.nkrasnovoronka.todoapp.service.impl;

import com.nkrasnovoronka.todoapp.dto.project.RequestProject;
import com.nkrasnovoronka.todoapp.dto.project.ResponseProject;
import com.nkrasnovoronka.todoapp.exception.ProjectNotFoundException;
import com.nkrasnovoronka.todoapp.mapper.ProjectMapper;
import com.nkrasnovoronka.todoapp.model.Project;
import com.nkrasnovoronka.todoapp.repo.ProjectRepository;
import com.nkrasnovoronka.todoapp.repo.UserRepository;
import com.nkrasnovoronka.todoapp.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

  private final ProjectRepository projectRepository;
  private final UserRepository userRepository;

  private final ProjectMapper projectMapper;

  @Override
  public ResponseProject createProject(RequestProject requestProject, Long id) {
    Project project = projectMapper.toEntity(requestProject);
    var user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Cannot find user with id " + id));
    project.setOwner(user);
    Project savedProject = projectRepository.save(project);
    return projectMapper.toDto(savedProject);
  }

  @Override
  public void addUserToProject(Long projectId, Long userId) {
    var project = projectRepository.findById(projectId)
        .orElseThrow(() -> new ProjectNotFoundException(projectId));
    var user = userRepository.findById(userId)
        .orElseThrow(() -> new UsernameNotFoundException("Cannot find user with id " + userId));

    user.getUserProjects().add(project);
    userRepository.save(user);
  }
}
