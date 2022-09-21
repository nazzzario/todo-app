package com.nkrasnovoronka.todoapp.controller;

import com.nkrasnovoronka.todoapp.dto.project.RequestProject;
import com.nkrasnovoronka.todoapp.dto.project.ResponseProject;
import com.nkrasnovoronka.todoapp.dto.user.ResponseUser;
import com.nkrasnovoronka.todoapp.security.AppUserDetailsImpl;
import com.nkrasnovoronka.todoapp.service.ProjectService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/projects")
@RequiredArgsConstructor
public class ProjectController {

  private final ProjectService projectService;

  @PostMapping("/create")
  public ResponseEntity<ResponseProject> createProject(Authentication authentication,
                                                       @Valid @RequestBody RequestProject requestProject) {
    var userDetails = (AppUserDetailsImpl) authentication.getPrincipal();
    ResponseProject responseProject = projectService.createProject(requestProject, userDetails.getId());
    return ResponseEntity.ok(responseProject);
  }

  @GetMapping("/{projectId}/users")
  public ResponseEntity<List<ResponseUser>> allProjectUsers(@PathVariable Long projectId) {
    return ResponseEntity.ok(projectService.getAllProjectUsers(projectId));
  }

  @DeleteMapping("/{projectId}/delete")
  public ResponseEntity<HttpStatus> deleteProject(@PathVariable Long projectId) {
    projectService.deleteProjectById(projectId);
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/{projectId}/update")
  public ResponseEntity<ResponseProject> updateProject(@PathVariable Long projectId,
                                                       @Valid @RequestBody RequestProject requestProject) {
    var responseProject = projectService.updateProject(projectId, requestProject);
    return ResponseEntity.ok(responseProject);
  }

  @GetMapping("/assign/{projectId}/{userId}")
  public void addUserToProject(@PathVariable Long projectId, @PathVariable Long userId) {
    projectService.addUserToProject(projectId, userId);
  }

  @GetMapping("/remove/{projectId}/{userId}")
  public void removeUserFromProject(@PathVariable Long projectId, @PathVariable Long userId) {
    projectService.removeUserFromProject(projectId, userId);
  }
}
