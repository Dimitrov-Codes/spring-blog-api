package com.springboot.blog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Schema(description = "PostDTO Model Information")
public class PostDTO {

    private Long id;

    @Schema(description = "Post Title")
    @NotEmpty(message = "Title should not be empty")
    @Size(min = 3)
    private String title;

    @Schema(description = "Post Description")
    @NotEmpty(message = "Description should not be empty")
    private String description;

    @Schema(description = "Post Content")
    @NotEmpty(message = "Content should not be empty")
    private String content;

    @Schema(description = "Post Category")
    @NotNull(message = "Category ID must be mentioned")
    private Long categoryId;


    @Schema(description = "Post Comments")
    private List<CommentDTO> comments;
}
