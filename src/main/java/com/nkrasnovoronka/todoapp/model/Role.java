package com.nkrasnovoronka.todoapp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

@Table(name = "roles")
@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Role {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "role_name")
  private String roleName;

  @ManyToMany(mappedBy = "roles")
  @ToString.Exclude
  private List<AppUser> appUserList = new ArrayList<>();

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    Role role = (Role) o;
    return id != null && Objects.equals(id, role.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
