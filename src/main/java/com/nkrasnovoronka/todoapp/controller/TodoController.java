package com.nkrasnovoronka.todoapp.controller;

import com.nkrasnovoronka.todoapp.dto.todo.RequestTodo;
import com.nkrasnovoronka.todoapp.dto.todo.ResponseTodo;
import com.nkrasnovoronka.todoapp.security.AppUserDetailsImpl;
import com.nkrasnovoronka.todoapp.service.TodoService;
import java.util.List;
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


  @PostMapping("{projectId}/create")
  public ResponseEntity<ResponseTodo> createTodo(Authentication authentication,
                                                 @PathVariable Long projectId,
                                                 @RequestBody RequestTodo requestTodo) {
    var authUser = (AppUserDetailsImpl) authentication.getPrincipal();
    var responseTodo = todoService.createTodo(requestTodo, projectId, authUser.getId());
    return ResponseEntity.ok(responseTodo);
  }

  @GetMapping("{projectId}/get/{todoId}")
  public ResponseEntity<ResponseTodo> getTodo(@PathVariable Long projectId, @PathVariable Long todoId){
    var responseTodo = todoService.getProjectToDoById(projectId, todoId);
    return ResponseEntity.ok(responseTodo);
  }

  @PutMapping("{projectId}/update/{todoId}")
  public ResponseEntity<ResponseTodo> updateTodo(@PathVariable Long projectId,
                                                 @PathVariable Long todoId,
                                                 @RequestBody RequestTodo requestTodo){

    ResponseTodo todo = todoService.updateTodo(todoId, requestTodo);
    return ResponseEntity.ok(todo);
  }

  @GetMapping("{projectId}/all")
  public ResponseEntity<List<ResponseTodo>> getAllProjectTodos(@PathVariable Long projectId){
    var projectTodosList = todoService.getAllProjectTodos(projectId);
    return ResponseEntity.ok(projectTodosList);
  }

  @DeleteMapping("{projectId}/delete/{todoId}")
  public ResponseEntity<HttpStatus> deleteProjectId(@PathVariable Long projectId, @PathVariable Long todoId){
    todoService.deleteProjectTodoById(projectId, todoId);
    return ResponseEntity.noContent().build();
  }

}
