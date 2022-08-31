package com.nkrasnovoronka.todoapp.service;

import com.nkrasnovoronka.todoapp.dto.todo.RequestTodo;
import com.nkrasnovoronka.todoapp.dto.todo.ResponseTodo;
import java.util.List;

public interface TodoService {
  ResponseTodo createTodo(RequestTodo requestTodo, Long projectId, Long creatorId);

  List<ResponseTodo> getAllProjectTodos(Long projectId);

  void deleteProjectTodoById(Long projectId, Long todoId);

  ResponseTodo getProjectToDoById(Long projectId, Long todoId);

  ResponseTodo updateTodo(Long todoId, RequestTodo requestTodo);
}
