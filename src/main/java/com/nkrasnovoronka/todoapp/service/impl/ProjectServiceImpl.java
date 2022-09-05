package com.nkrasnovoronka.todoapp.service.impl;

import com.nkrasnovoronka.todoapp.dto.project.RequestProject;
import com.nkrasnovoronka.todoapp.dto.project.ResponseProject;
import com.nkrasnovoronka.todoapp.dto.user.ResponseUser;
import com.nkrasnovoronka.todoapp.exception.ProjectNotFoundException;
import com.nkrasnovoronka.todoapp.exception.TodoAppException;
import com.nkrasnovoronka.todoapp.mapper.ProjectMapper;
import com.nkrasnovoronka.todoapp.mapper.UserMapper;
import com.nkrasnovoronka.todoapp.model.Project;
import com.nkrasnovoronka.todoapp.repo.ProjectRepository;
import com.nkrasnovoronka.todoapp.repo.UserRepository;
import com.nkrasnovoronka.todoapp.service.ProjectService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

  private final ProjectRepository projectRepository;
  private final UserRepository userRepository;
  private final ProjectMapper projectMapper;

  private final UserMapper userMapper;

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

  @Override
  public void deleteProjectById(Long projectId) {
    projectRepository.deleteById(projectId);
  }

  @Override
  public List<ResponseUser> getAllProjectUsers(Long projectId) {
    return userRepository.findAllByProjectId(projectId).stream()
        .map(userMapper::toDto)
        .toList();
  }

  @Override
  public ResponseProject updateProject(Long projectId, RequestProject requestProject) {
    var projectFromDb = projectRepository.findById(projectId)
        .orElseThrow(() -> new TodoAppException("Cannot find project with id " + projectId));
    Optional.ofNullable(requestProject.projectName()).ifPresent(projectFromDb::setProjectName);
    var updatedProject = projectRepository.save(projectFromDb);
    return projectMapper.toDto(updatedProject);
  }
}
