package com.nkrasnovoronka.todoapp.controller;

import com.nkrasnovoronka.todoapp.dto.project.RequestProject;
import com.nkrasnovoronka.todoapp.dto.project.ResponseProject;
import com.nkrasnovoronka.todoapp.security.AppUserDetailsImpl;
import com.nkrasnovoronka.todoapp.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/projects")
@RequiredArgsConstructor
public class ProjectController {

  private final ProjectService projectService;

  @PostMapping("/create")
  public ResponseEntity<?> createProject(Authentication authentication, @RequestBody RequestProject requestProject){
    var userDetails = (AppUserDetailsImpl) authentication.getPrincipal();
    ResponseProject responseProject = projectService.createProject(requestProject, userDetails.getId());
    return ResponseEntity.ok(responseProject);
  }

  @GetMapping("/assign/{projectId}/{userId}")
  public void addUserToProject(@PathVariable Long projectId, @PathVariable Long userId){
    projectService.addUserToProject(projectId, userId);
  }
}
