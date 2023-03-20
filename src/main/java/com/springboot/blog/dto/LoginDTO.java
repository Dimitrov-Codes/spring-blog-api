package com.springboot.blog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "LoginDTO Model Information")
public class LoginDTO {
     @Schema(description = "Username or Email")
     @NotEmpty
     private String usernameOrEmail;

     @Schema(description = "User Password")
     @NotEmpty
     private String password;
}
