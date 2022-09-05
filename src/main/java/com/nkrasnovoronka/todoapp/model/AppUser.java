package com.nkrasnovoronka.todoapp.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class AppUser {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  private String password;

  private String email;

  @Column(name = "activation_code")
  private String activationCode;

  @Column(name = "user_status")
  @Enumerated(EnumType.STRING)
  private UserStatus userStatus;

  @JsonManagedReference
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "user_roles",
      joinColumns = {@JoinColumn(name = "user_id")},
      inverseJoinColumns = {@JoinColumn(name = "role_id")})
  @ToString.Exclude
  private List<Role> roles = new ArrayList<>();

  @JsonManagedReference
  @OneToMany(mappedBy = "owner")
  @ToString.Exclude
  private List<Project> projects = new ArrayList<>();

  @ManyToMany
  @JoinTable(name = "user_projects",
      joinColumns = {@JoinColumn(name = "user_id")},
      inverseJoinColumns = {@JoinColumn(name = "project_id")})
  @ToString.Exclude
  private List<Project> userProjects = new ArrayList<>();

  @ManyToMany
  @JoinTable(name = "todo_coloborants",
      joinColumns = {@JoinColumn(name = "user_id")},
      inverseJoinColumns = {@JoinColumn(name = "todo_id")})
  @ToString.Exclude
  private List<Todo> userTodos = new ArrayList<>();

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

  @PrePersist
  private void setCreatedAt() {
    createdAt = LocalDateTime.now();
    userStatus = UserStatus.NOT_ACTIVATED;
  }

  @PreUpdate
  private void setUpdatedAt() {
    updatedAt = LocalDateTime.now();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof AppUser appUser)) return false;
    return Objects.equals(id, appUser.id) && Objects.equals(firstName, appUser.firstName) && Objects.equals(lastName, appUser.lastName) && Objects.equals(password, appUser.password) && Objects.equals(email, appUser.email) && Objects.equals(userStatus, appUser.userStatus) && Objects.equals(roles, appUser.roles) && Objects.equals(projects, appUser.projects) && Objects.equals(userProjects, appUser.userProjects) && Objects.equals(userTodos, appUser.userTodos) && Objects.equals(createdAt, appUser.createdAt) && Objects.equals(updatedAt, appUser.updatedAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, firstName, lastName, password, email, userStatus, roles, projects, userProjects, userTodos, createdAt, updatedAt);
  }
}
