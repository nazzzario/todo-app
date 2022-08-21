package com.nkrasnovoronka.todoapp.exception;

public class ProjectNotFoundException extends RuntimeException {
  public ProjectNotFoundException(Long projectId) {
    super("Cannot find project with id " + projectId);
  }
}
