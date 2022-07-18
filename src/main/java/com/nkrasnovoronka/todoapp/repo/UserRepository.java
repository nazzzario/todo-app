package com.nkrasnovoronka.todoapp.repo;

import com.nkrasnovoronka.todoapp.model.AppUser;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {

  Optional<AppUser> findByEmail(String email);
}
