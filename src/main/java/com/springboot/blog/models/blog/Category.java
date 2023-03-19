package com.springboot.blog.models.blog;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
public class Category {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;
     private String name;
     private String description;

     @Override
     public boolean equals(Object o) {
          if (this == o) return true;
          if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
          Category category = (Category) o;
          return getId() != null && Objects.equals(getId(), category.getId());
     }

     @Override
     public int hashCode() {
          return getClass().hashCode();
     }
}
