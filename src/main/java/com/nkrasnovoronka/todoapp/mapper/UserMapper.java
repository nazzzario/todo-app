package com.nkrasnovoronka.todoapp.mapper;

import com.nkrasnovoronka.todoapp.dto.user.RequestUser;
import com.nkrasnovoronka.todoapp.dto.user.ResponseUser;
import com.nkrasnovoronka.todoapp.model.AppUser;
import com.nkrasnovoronka.todoapp.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", imports = {Role.class})
public interface UserMapper {

  @Mapping(target = "userStatus", ignore = true)
  @Mapping(target = "activationCode", ignore = true)
  @Mapping(target = "userTodos", ignore = true)
  @Mapping(target = "userProjects", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "roles", ignore = true)
  @Mapping(target = "projects", ignore = true)
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "firstName", source = "firstName")
  @Mapping(target = "lastName", source = "lastName")
  @Mapping(target = "password", source = "password")
  @Mapping(target = "email", source = "email")
  AppUser toEntity(RequestUser user);

  @Mapping(target = "roles", expression = "java(appUser.getRoles().stream().map(com.nkrasnovoronka.todoapp.model.Role::getRoleName).toList())")
  ResponseUser toDto(AppUser appUser);
}
