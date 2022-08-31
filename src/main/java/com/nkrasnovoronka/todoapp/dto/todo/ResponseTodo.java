package com.nkrasnovoronka.todoapp.dto.todo;

import com.nkrasnovoronka.todoapp.model.Status;

public record ResponseTodo(Long id,
                           String title,
                           String description,
                           String name,
                           String projectName,
                           Status status) {
}
