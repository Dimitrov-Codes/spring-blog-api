package com.springboot.blog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "RegisterDTO Model Information")
public class RegisterDTO {
     @Schema(description = "New User Name")
     @NotEmpty(message = "Name cannot be empty")
     private String name;

     @Schema(description = "New User Email")
     @NotEmpty(message = "Email cannot be empty")
     @Email
     private String email;

     @Schema(description = "Username")
     @NotEmpty(message = "Username cannot be empty")
     @Size(min = 3, max = 10)
     private String username;

     @Schema(description = "New User Password")
     @NotEmpty(message = "Password cannot be empty")
     private String password;
}
