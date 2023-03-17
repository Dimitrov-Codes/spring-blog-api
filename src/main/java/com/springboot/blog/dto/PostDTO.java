package com.springboot.blog.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
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
    private List<CommentDTO> comments;
}
