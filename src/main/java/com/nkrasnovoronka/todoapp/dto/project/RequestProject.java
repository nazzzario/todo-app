package com.nkrasnovoronka.todoapp.dto.project;

import javax.validation.constraints.NotBlank;

public record RequestProject(
    @NotBlank
    String projectName) {

}
