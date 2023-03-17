package com.springboot.blog.controller;

import com.springboot.blog.dto.CommentDTO;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService commentService;
    private final CommentRepository commentRepository;

    public CommentController(CommentService commentService, CommentRepository commentRepository) {
        this.commentService = commentService;
        this.commentRepository = commentRepository;
    }

    @GetMapping("getAllComments")
    public List<CommentDTO> getAllCommentsFromPost() {
        return commentRepository.findAll().stream().map(commentService::mapToDTO).collect(Collectors.toList());
    }

    @GetMapping("/{commentId}")
    public CommentDTO getCommentFromPost(@PathVariable Long commentId) {
        return commentService.mapToDTO(commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId)));
    }

    @PostMapping("/createComment/{postId}")
    @ResponseStatus(code = HttpStatus.CREATED)
    public CommentDTO createComment(@RequestBody @Valid CommentDTO comment, @PathVariable Long postId) {
        return commentService.addComment(postId, comment);
    }

    @DeleteMapping("/{commentId}")
    public CommentDTO deleteComment(@PathVariable Long commentId) {
        return commentService.deleteComment(commentId);
    }
    @PutMapping("/update/{id}")
    public CommentDTO updateCommet(@RequestBody @Valid CommentDTO commentDTO, @PathVariable Long id){
        return commentService.updateComment(commentDTO, id);
    }
}
