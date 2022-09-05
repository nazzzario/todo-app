package com.nkrasnovoronka.todoapp.service.impl;

import com.nkrasnovoronka.todoapp.dto.todo.RequestTodo;
import com.nkrasnovoronka.todoapp.dto.todo.ResponseTodo;
import com.nkrasnovoronka.todoapp.exception.ProjectNotFoundException;
import com.nkrasnovoronka.todoapp.exception.TodoAppException;
import com.nkrasnovoronka.todoapp.mapper.TodoMapper;
import com.nkrasnovoronka.todoapp.model.TodoStatus;
import com.nkrasnovoronka.todoapp.model.Todo;
import com.nkrasnovoronka.todoapp.repo.ProjectRepository;
import com.nkrasnovoronka.todoapp.repo.TodoRepository;
import com.nkrasnovoronka.todoapp.repo.UserRepository;
import com.nkrasnovoronka.todoapp.service.TodoService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TodoServiceImpl implements TodoService {

  private final TodoRepository todoRepository;

  private final ProjectRepository projectRepository;

  private final UserRepository userRepository;

  private final TodoMapper todoMapper;

  @Override
  public ResponseTodo createTodo(RequestTodo requestTodo, Long projectId, Long creatorId) {
    Todo todo = todoMapper.toEntity(requestTodo);
    var projectById = projectRepository.findById(projectId)
        .orElseThrow(() -> new ProjectNotFoundException(projectId));

    var userById = userRepository.findById(creatorId)
        .orElseThrow(() -> new UsernameNotFoundException("Cannot find user with id " + creatorId));

    todo.setTodoStatus(TodoStatus.CREATED);
    todo.setCreator(userById);
    todo.setProject(projectById);

    var savedTodo = todoRepository.save(todo);

    return todoMapper.toResponse(savedTodo);
  }

  @Override
  @Transactional(readOnly = true)
  public List<ResponseTodo> getAllProjectTodos(Long projectId) {
    return todoRepository.findAllByProjectId(projectId).stream()
        .map(todoMapper::toResponse)
        .toList();
  }

  @Override
  public void deleteProjectTodoById(Long projectId, Long todoId) {
    todoRepository.deleteById(todoId);
  }

  @Override
  public ResponseTodo getProjectToDoById(Long projectId, Long todoId) {
    return todoRepository.findById(todoId)
        .map(todoMapper::toResponse)
        .orElseThrow(() -> new TodoAppException("Todo not found"));
  }

  @Override
  public ResponseTodo updateTodo(Long todoId, RequestTodo requestTodo) {
    var todoFromDb = todoRepository.findById(todoId).orElseThrow(() -> new TodoAppException("Cannot find todo with id" + todoId));
    Optional.ofNullable(requestTodo.title()).ifPresent(todoFromDb::setTitle);
    Optional.ofNullable(requestTodo.description()).ifPresent(todoFromDb::setDescription);
    Optional.ofNullable(requestTodo.todoStatus()).ifPresent(todoFromDb::setTodoStatus);
    Optional.ofNullable(requestTodo.finishedAt()).ifPresent(todoFromDb::setFinishedAt);
    var savedTodo = todoRepository.save(todoFromDb);
    return todoMapper.toResponse(savedTodo);
  }
}
