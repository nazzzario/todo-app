package com.nkrasnovoronka.todoapp.repo;

import com.nkrasnovoronka.todoapp.model.AppUser;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {

  @Query(value = "select * from users u " +
      "join user_projects up on u.id = up.user_id " +
      "join projects p on p.id = up.project_id " +
      "where p.id = :projectId", nativeQuery = true)
  List<AppUser> findAllByProjectId(@Param("projectId") Long projectId);

  Optional<AppUser> findByEmail(String email);
}
