package com.springboot.blog.controller;

import com.springboot.blog.dto.PostDTO;
import com.springboot.blog.dto.PostPage;
import com.springboot.blog.service.PostService;
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

@RestController
@RequestMapping("/api/posts")
@Tag(name = "CRUD REST APIs for Post Resource")
public class PostController {

     private final PostService postService;

     public PostController(PostService postService) {
          this.postService = postService;
     }

     @Operation(
             summary = "Get All Posts REST API",
             description = "Get All Posts from the database"
     )
     @ApiResponse(
             responseCode = "200",
             description = "HTTP Status 200 SUCCESS"
     )
     @GetMapping("/getAllPosts")
     public List<PostDTO> getAllPosts() {
          return postService.getAllPosts();
     }

     @Operation(
             summary = "Get Post REST API",
             description = "Get a single Post from the database"
     )
     @ApiResponse(
             responseCode = "200",
             description = "HTTP Status 200 SUCCESS"
     )
     @GetMapping("/{id}")
     public ResponseEntity<PostDTO> getPostById(@PathVariable(name = "id") long id) {
          return ResponseEntity.ok(postService.getPostById(id));
     }

     @Operation(
             summary = "Get Paged Post REST API",
             description = "Get a group of Posts as a Page from the database"
     )
     @ApiResponse(
             responseCode = "200",
             description = "HTTP Status 200 SUCCESS"
     )
     @GetMapping
     public PostPage getPostsByPage(@RequestParam(defaultValue = "0", required = false) Integer pageNo, @RequestParam(defaultValue = "10", required = false) Integer pageSize, @RequestParam(defaultValue = "id", required = false) String sortBy, @RequestParam(defaultValue = "asc", required = false) String sortDir) {
          return postService.getPostsByPage(pageNo, pageSize, sortBy, sortDir);
     }

     @Operation(
             summary = "Create Post REST API",
             description = "Create Post API is used to add a new post with no comments to the database"
     )
     @ApiResponse(
             responseCode = "201",
             description = "HTTP Status 201 CREATED"
     )
     @SecurityRequirement(name = "Bearer Authentication")
     @PreAuthorize("hasRole('ADMIN')")
     @PostMapping
     public ResponseEntity<PostDTO> createPost(@RequestBody @Valid PostDTO postDto) {
          return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
     }

     @Operation(
             summary = "Update Post REST API",
             description = "Update a single Post"
     )
     @ApiResponse(
             responseCode = "200",
             description = "HTTP Status 200 SUCCESS"
     )
     @SecurityRequirement(name = "Bearer Authentication")
     @PreAuthorize("hasRole('ADMIN')")
     @PutMapping("/{id}")
     public ResponseEntity<PostDTO> updatePost(@RequestBody @Valid PostDTO postDto,
                                               @PathVariable(name = "id") long id) {
          PostDTO postResponse = postService.updatePost(postDto, id);
          return new ResponseEntity<>(postResponse, HttpStatus.OK);
     }

     @Operation(
             summary = "Delete Post REST API",
             description = "Delete a single Post permanently"
     )
     @ApiResponse(
             responseCode = "200",
             description = "HTTP Status 200 SUCCESS"
     )
     @SecurityRequirement(name = "Bearer Authentication")
     @PreAuthorize("hasRole('ADMIN')")
     @DeleteMapping("/{id}")
     public ResponseEntity<String> deletePost(@PathVariable(name = "id") Long id) {
          postService.deletePostById(id);
          return new ResponseEntity<>("Post entity deleted successfully.", HttpStatus.OK);
     }
}
