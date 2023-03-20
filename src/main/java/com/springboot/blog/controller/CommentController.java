package com.springboot.blog.controller;

import com.springboot.blog.dto.CommentDTO;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/comments")
@Tag(name = "CRUD REST APIs for Comment Resource")
public class CommentController {
     private final CommentService commentService;
     private final CommentRepository commentRepository;

     public CommentController(CommentService commentService, CommentRepository commentRepository) {
          this.commentService = commentService;
          this.commentRepository = commentRepository;
     }
     @Operation(
             summary = "Get All Comments REST API",
             description = "Get All Comments from the database"
     )
     @ApiResponse(
             responseCode = "200",
             description = "HTTP Status 200 SUCCESS"
     )
     @GetMapping("getAllComments")
     public List<CommentDTO> getAllCommentsFromPost() {
          return commentRepository.findAll().stream().map(commentService::mapToDTO).collect(Collectors.toList());
     }
     @Operation(
             summary = "Get Comment REST API",
             description = "Get a Single Comment from the database"
     )
     @ApiResponse(
             responseCode = "200",
             description = "HTTP Status 200 SUCCESS"
     )
     @GetMapping("/{commentId}")
     public CommentDTO getCommentFromPost(@PathVariable Long commentId) {
          return commentService.mapToDTO(commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId)));
     }
     @Operation(
             summary = "Create Comment REST API",
             description = "Create a Single Comment and add it to the database"
     )
     @ApiResponse(
             responseCode = "201",
             description = "HTTP Status 201 CREATED"
     )
     @SecurityRequirement(name = "Bearer Authentication")
     @PreAuthorize("hasRole('ADMIN')")
     @PostMapping("/createComment/{postId}")
     @ResponseStatus(code = HttpStatus.CREATED)
     public CommentDTO createComment(@RequestBody @Valid CommentDTO comment, @PathVariable Long postId) {
          return commentService.addComment(postId, comment);
     }
     @Operation(
             summary = "Delete Comment REST API",
             description = "Delete a Single Comment and from the database"
     )
     @ApiResponse(
             responseCode = "200",
             description = "HTTP Status 200 SUCCESS"
     )
     @SecurityRequirement(name = "Bearer Authentication")
     @PreAuthorize("hasRole('ADMIN')")
     @DeleteMapping("/{commentId}")
     public CommentDTO deleteComment(@PathVariable Long commentId) {
          return commentService.deleteComment(commentId);
     }

     @Operation(
             summary = "Update Comment REST API",
             description = "Update a Single Comment and add it back to the database"
     )
     @ApiResponse(
             responseCode = "200",
             description = "HTTP Status 200 SUCCESS"
     )
     @SecurityRequirement(name = "Bearer Authentication")
     @PreAuthorize("hasRole('ADMIN')")
     @PutMapping("/update/{id}")
     public CommentDTO updateComment(@RequestBody @Valid CommentDTO commentDTO, @PathVariable Long id) {
          return commentService.updateComment(commentDTO, id);
     }
}
