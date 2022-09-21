package com.nkrasnovoronka.todoapp.controller;

import com.nkrasnovoronka.todoapp.dto.todo.RequestTodo;
import com.nkrasnovoronka.todoapp.dto.todo.ResponseTodo;
import com.nkrasnovoronka.todoapp.security.AppUserDetailsImpl;
import com.nkrasnovoronka.todoapp.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@RequestMapping("api/v1/todos")
@RequiredArgsConstructor
public class TodoController {

  private final TodoService todoService;

  @Operation(summary = "Create ToDo task in project")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Create ToDo", content =
          {@Content(mediaType = "application/json")})
  }
  )
  @PostMapping("{projectId}/create")
  public ResponseEntity<ResponseTodo> createTodo(Authentication authentication,
                                                 @Parameter(description = "id of project where ToDo be created")
                                                 @PathVariable Long projectId,
                                                 @Valid @RequestBody RequestTodo requestTodo) {
    var authUser = (AppUserDetailsImpl) authentication.getPrincipal();
    var responseTodo = todoService.createTodo(requestTodo, projectId, authUser.getId());
    return ResponseEntity.ok(responseTodo);
  }

  @GetMapping("{projectId}/get/{todoId}")
  public ResponseEntity<ResponseTodo> getTodo(@PathVariable Long projectId, @PathVariable Long todoId) {
    var responseTodo = todoService.getProjectToDoById(projectId, todoId);
    return ResponseEntity.ok(responseTodo);
  }

  @PutMapping("{projectId}/update/{todoId}")
  public ResponseEntity<ResponseTodo> updateTodo(@PathVariable Long projectId,
                                                 @PathVariable Long todoId,
                                                 @Valid @RequestBody RequestTodo requestTodo) {

    ResponseTodo todo = todoService.updateTodo(todoId, requestTodo);
    return ResponseEntity.ok(todo);
  }

  @GetMapping("{projectId}/all")
  public ResponseEntity<List<ResponseTodo>> getAllProjectTodos(@PathVariable Long projectId) {
    var projectTodosList = todoService.getAllProjectTodos(projectId);
    return ResponseEntity.ok(projectTodosList);
  }

  @DeleteMapping("{projectId}/delete/{todoId}")
  public ResponseEntity<HttpStatus> deleteProjectId(@PathVariable Long projectId, @PathVariable Long todoId) {
    todoService.deleteProjectTodoById(projectId, todoId);
    return ResponseEntity.noContent().build();
  }

}
