package com.springboot.blog.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor

public class PostDTO {
    private Long id;
    @NotEmpty(message = "Title should not be empty")
    @Size(min = 3)
    private String title;
    @NotEmpty(message = "Description should not be empty")
    private String description;
    @NotEmpty(message = "Content should not be empty")
    private String content;
    @NotNull(message = "Category ID must be mentioned")
    private Long categoryId;
    private List<CommentDTO> comments;
}
