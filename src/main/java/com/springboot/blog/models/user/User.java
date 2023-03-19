package com.springboot.blog.models.user;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class User {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;

     private String name;

     @Column(nullable = false, unique = true)
     private String email;

     @Column(nullable = false, unique = true)
     private String username;

     @Column(nullable = false)
     private String password;

     @ManyToMany(fetch = FetchType.EAGER)
     // Eager loading allows the roles to be loaded during the UserDetails extraction
     @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
             inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
     private Set<Role> roles = new HashSet<>();// If new HashSet is not created initialization is possible through setters or constructor
}
