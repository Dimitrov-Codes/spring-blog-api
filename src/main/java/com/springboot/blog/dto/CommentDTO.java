package com.springboot.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

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
