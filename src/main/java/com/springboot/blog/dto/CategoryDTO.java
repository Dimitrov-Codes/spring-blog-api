package com.springboot.blog.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
     private Long id;
     @Size(min = 3, max = 10)
     private String name;
     @Size(min = 3)
     private String description;
}
