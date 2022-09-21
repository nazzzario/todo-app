package com.nkrasnovoronka.todoapp.mapper;

import com.nkrasnovoronka.todoapp.dto.todo.RequestTodo;
import com.nkrasnovoronka.todoapp.dto.todo.ResponseTodo;
import com.nkrasnovoronka.todoapp.model.Todo;
import com.nkrasnovoronka.todoapp.service.ProjectService;
import com.nkrasnovoronka.todoapp.service.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ProjectService.class, UserService.class})
public interface TodoMapper {

  @Mapping(target = "todoStatus", ignore = true)
  @Mapping(target = "project", ignore = true)
  @Mapping(target = "creator", ignore = true)
  @Mapping(target = "title", source = "title")
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "finishedAt", source = "finishedAt")
  @Mapping(target = "description", source = "description")
  @Mapping(target = "coloborants", ignore = true)
  Todo toEntity(RequestTodo requestTodo);


  @Mapping(target = "projectName", source = "project.projectName")
  @Mapping(target = "name", source = "creator.email")
  ResponseTodo toResponse(Todo savedTodo);

}
