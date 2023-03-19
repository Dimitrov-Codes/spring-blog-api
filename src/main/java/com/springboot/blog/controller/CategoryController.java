package com.springboot.blog.controller;

import com.springboot.blog.dto.CategoryDTO;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.models.blog.Category;
import com.springboot.blog.repository.CategoryRepository;
import com.springboot.blog.service.CategoryService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categories/")
public class CategoryController {
     private final CategoryService categoryService;
     private final CategoryRepository categoryRepository;

     public CategoryController(CategoryService categoryService,
                               CategoryRepository categoryRepository) {
          this.categoryService = categoryService;
          this.categoryRepository = categoryRepository;
     }
     @GetMapping
     public ResponseEntity<List<CategoryDTO>> getAllCategories(){
          return ResponseEntity.ok(categoryRepository.findAll()
                  .stream()
                  .map(categoryService::mapToDTO)
                  .collect(Collectors.toList()));
     }
     @GetMapping("{id}")
     public ResponseEntity<CategoryDTO> getCategory(@PathVariable Long id){
          Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
          return ResponseEntity.ok(categoryService.mapToDTO(category));
     }
     @PreAuthorize("hasRole('ADMIN')")
     @PostMapping("/addCategory")
     public ResponseEntity<CategoryDTO> addCategory(@RequestBody @Valid CategoryDTO categoryDTO){
          return new ResponseEntity<>(categoryService.addCategory(categoryDTO), HttpStatus.CREATED);
     }
     @PreAuthorize("hasRole('ADMIN')")
     @DeleteMapping("{id}")
     public ResponseEntity<String> deleteCategory(@PathVariable Long id){
          categoryService.deleteCategory(id);
          return ResponseEntity.ok("Deleted category successfully");
     }
     @PreAuthorize("hasRole('ADMIN')")
     @PutMapping("{id}")
     public ResponseEntity<CategoryDTO> updateCategory(@RequestBody @Valid CategoryDTO categoryDTO,
                                                  @PathVariable Long id){
          categoryService.updateCategory(categoryDTO, id);
          return ResponseEntity.ok(categoryService.updateCategory(categoryDTO, id));
     }

}
