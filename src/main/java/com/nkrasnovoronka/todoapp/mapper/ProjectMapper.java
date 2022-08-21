package com.nkrasnovoronka.todoapp.mapper;

import com.nkrasnovoronka.todoapp.dto.project.RequestProject;
import com.nkrasnovoronka.todoapp.dto.project.ResponseProject;
import com.nkrasnovoronka.todoapp.model.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

  //todo improve mapper
  @Mapping(target = "projectUsers", ignore = true)
  @Mapping(target = "projectName", source = "projectName")
  @Mapping(target = "owner", ignore = true)
  @Mapping(target = "id", ignore = true)
  Project toEntity(RequestProject requestProject);


  ResponseProject toDto(Project project);
}
