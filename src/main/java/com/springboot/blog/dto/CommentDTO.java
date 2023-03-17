package com.springboot.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
    private Long id;
    @NotEmpty(message = "Comment must not be empty")
    private String body;
    @Email
    @NotEmpty(message = "Email must not be empty")
    private String email;
    @NotEmpty
    private String name;
}
