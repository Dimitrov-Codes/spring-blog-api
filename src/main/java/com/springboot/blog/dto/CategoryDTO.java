package com.springboot.blog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "CategoryDTO Model Information")
public class CategoryDTO {
     private Long id;

     @Schema(description = "Category Title")
     @Size(min = 3, max = 10)
     private String name;

     @Schema(description = "Category Description")
     @Size(min = 3)
     private String description;
}
