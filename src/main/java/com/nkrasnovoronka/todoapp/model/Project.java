package com.nkrasnovoronka.todoapp.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

@Table(name = "projects")
@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Project {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "project_name")
  private String projectName;

  @JsonBackReference
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "owner_id", nullable = false)
  @ToString.Exclude
  private AppUser owner;

  @JsonBackReference
  @ManyToMany(mappedBy = "userProjects")
  @ToString.Exclude
  private List<AppUser> projectUsers = new ArrayList<>();

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    Project project = (Project) o;
    return id != null && Objects.equals(id, project.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
