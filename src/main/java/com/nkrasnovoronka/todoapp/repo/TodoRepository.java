package com.nkrasnovoronka.todoapp.repo;

import com.nkrasnovoronka.todoapp.model.Todo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
  List<Todo> findAllByProjectId(Long projectId);
}
