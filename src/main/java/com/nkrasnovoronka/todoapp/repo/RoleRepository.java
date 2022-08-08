package com.nkrasnovoronka.todoapp.repo;

import com.nkrasnovoronka.todoapp.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

  Role findRoleByRoleName(String roleName);

}
