package com.springboot.blog.service;

import com.springboot.blog.dto.CategoryDTO;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.models.blog.Category;
import com.springboot.blog.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
     private final CategoryRepository categoryRepository;
     private final ModelMapper mapper;

     public CategoryService(CategoryRepository categoryRepository, ModelMapper mapper) {
          this.categoryRepository = categoryRepository;
          this.mapper = mapper;
     }

     public CategoryDTO addCategory(CategoryDTO categoryDTO){
          Category detachedCategory = mapToEntity(categoryDTO);
          CategoryDTO savedCategory = mapToDTO(categoryRepository.save(detachedCategory));
          return savedCategory;
     }
     public CategoryDTO mapToDTO(Category category){
          return mapper.map(category, CategoryDTO.class);
     }

     public Category mapToEntity(CategoryDTO category){
          return mapper.map(category, Category.class);
     }

     public void deleteCategory(Long id) {
          categoryRepository.deleteById(id);
     }

     public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long id) {
          Category detachedCategory = mapper.map(categoryDTO, Category.class);
          if(categoryRepository.existsById(id))
               detachedCategory.setId(id);
          else throw new ResourceNotFoundException("Category", "id", id);
          return mapToDTO(categoryRepository.save(detachedCategory));
     }
}
