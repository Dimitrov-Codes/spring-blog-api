package com.springboot.blog.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDTO {
     @NotEmpty(message = "Name cannot be empty")
     private String name;

     @NotEmpty(message = "Email cannot be empty")
     @Email
     private String email;

     @NotEmpty(message = "Username cannot be empty")
     @Size(min = 3, max = 10)
     private String username;

     @NotEmpty(message = "Password cannot be empty")
     private String password;
}
