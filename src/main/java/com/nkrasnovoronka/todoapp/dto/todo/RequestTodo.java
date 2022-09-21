package com.nkrasnovoronka.todoapp.dto.todo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nkrasnovoronka.todoapp.anotations.EnumNamePattern;
import com.nkrasnovoronka.todoapp.model.TodoStatus;
import java.time.LocalDateTime;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public record RequestTodo(
    @Size(min = 1, max = 50)
    @NotBlank
    String title,
    String description,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    LocalDateTime finishedAt,
    @EnumNamePattern(regexp = "CREATED | IN_PROGRESS | CHECKING | FINISHED", message = "Invalid todo status")
    TodoStatus todoStatus) {
}
