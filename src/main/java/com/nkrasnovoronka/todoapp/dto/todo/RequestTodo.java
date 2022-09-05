package com.nkrasnovoronka.todoapp.dto.todo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nkrasnovoronka.todoapp.model.TodoStatus;
import java.time.LocalDateTime;

public record RequestTodo(String title,
                          String description,
                          @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
                          LocalDateTime finishedAt,
                          TodoStatus todoStatus) {
}
