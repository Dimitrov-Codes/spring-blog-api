package com.springboot.blog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "CommentDTO Model Information")
public class CommentDTO {
     private Long id;

     @Schema(description = "Comment Body")
     @NotEmpty(message = "Comment must not be empty")
     private String body;

     @Schema(description = "User Email")
     @Email
     @NotEmpty(message = "Email must not be empty")
     private String email;

     @Schema(description = "User Name")
     @NotEmpty
     private String name;
}
