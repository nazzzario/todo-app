package com.nkrasnovoronka.todoapp.model;


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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

@Table(name = "todos")
@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Todo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;

  private String description;

  @Enumerated(EnumType.STRING)
  private Status status;

  @Column(name = "finish_date")
  private LocalDateTime finishedAt;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "project_id", nullable = false)
  @ToString.Exclude
  private Project project;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "creator_id", nullable = false)
  @ToString.Exclude
  private AppUser creator;

  @ManyToMany(mappedBy = "userTodos")
  @ToString.Exclude
  private List<AppUser> coloborants = new ArrayList<>();

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    Todo todo = (Todo) o;
    return id != null && Objects.equals(id, todo.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
