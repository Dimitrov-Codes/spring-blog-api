package com.springboot.blog.controller;

import com.springboot.blog.dto.CategoryDTO;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.models.blog.Category;
import com.springboot.blog.repository.CategoryRepository;
import com.springboot.blog.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categories/")
@Tag(name = "CRUD REST APIs for Category Resource")
public class CategoryController {
     private final CategoryService categoryService;
     private final CategoryRepository categoryRepository;

     public CategoryController(CategoryService categoryService,
                               CategoryRepository categoryRepository) {
          this.categoryService = categoryService;
          this.categoryRepository = categoryRepository;
     }
     @Operation(
             summary = "Get All Categories REST API",
             description = "Get All Categories from the database"
     )
     @ApiResponse(
             responseCode = "200",
             description = "HTTP Status 200 SUCCESS"
     )
     @GetMapping
     public ResponseEntity<List<CategoryDTO>> getAllCategories() {
          return ResponseEntity.ok(categoryRepository.findAll()
                  .stream()
                  .map(categoryService::mapToDTO)
                  .collect(Collectors.toList()));
     }
     @Operation(
             summary = "Get Category REST API",
             description = "Get a single Category from the database"
     )
     @ApiResponse(
             responseCode = "200",
             description = "HTTP Status 200 SUCCESS"
     )
     @GetMapping("{id}")
     public ResponseEntity<CategoryDTO> getCategory(@PathVariable Long id) {
          Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
          return ResponseEntity.ok(categoryService.mapToDTO(category));
     }
     @Operation(
             summary = "Create Category REST API",
             description = "Create a new Category and add it to the database"
     )
     @ApiResponse(
             responseCode = "201",
             description = "HTTP Status 201 Created"
     )
     @SecurityRequirement(name = "Bearer Authentication")
     @PreAuthorize("hasRole('ADMIN')")
     @PostMapping("/addCategory")
     public ResponseEntity<CategoryDTO> addCategory(@RequestBody @Valid CategoryDTO categoryDTO) {
          return new ResponseEntity<>(categoryService.addCategory(categoryDTO), HttpStatus.CREATED);
     }
     @Operation(
             summary = "Delete Category REST API",
             description = "Delete a single Category from the database"
     )
     @ApiResponse(
             responseCode = "200",
             description = "HTTP Status 200 SUCCESS"
     )
     @SecurityRequirement(name = "Bearer Authentication")
     @PreAuthorize("hasRole('ADMIN')")
     @DeleteMapping("{id}")
     public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
          categoryService.deleteCategory(id);
          return ResponseEntity.ok("Deleted category successfully");
     }
     @Operation(
             summary = "Update Category REST API",
             description = "Updates a single Category and adds it back to the database"
     )
     @ApiResponse(
             responseCode = "200",
             description = "HTTP Status 200 SUCCESS"
     )
     @SecurityRequirement(name = "Bearer Authentication")
     @PreAuthorize("hasRole('ADMIN')")
     @PutMapping("{id}")
     public ResponseEntity<CategoryDTO> updateCategory(@RequestBody @Valid CategoryDTO categoryDTO,
                                                       @PathVariable Long id) {
          categoryService.updateCategory(categoryDTO, id);
          return ResponseEntity.ok(categoryService.updateCategory(categoryDTO, id));
     }

}
