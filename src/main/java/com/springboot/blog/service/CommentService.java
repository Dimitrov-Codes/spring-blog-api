package com.springboot.blog.service;

import com.springboot.blog.dto.CommentDTO;
import com.springboot.blog.exception.BadRequestException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.models.blog.Comment;
import com.springboot.blog.models.blog.Post;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final ModelMapper mapper;

    public CommentService(CommentRepository commentRepository,
                          PostRepository postRepository, ModelMapper mapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    public CommentDTO addComment(Long postId, CommentDTO commentDTO) {
        Comment comment = mapToEntity(commentDTO);
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        comment.setPost(post);
        return mapToDTO(commentRepository.save(comment));

    }

    public Comment mapToEntity(CommentDTO commentDTO) {
        return mapper.map(commentDTO, Comment.class);
    }

    public CommentDTO mapToDTO(Comment comment) {
        return mapper.map(comment, CommentDTO.class);
    }

    public CommentDTO deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new BadRequestException("Comment", "id", commentId));
        commentRepository.deleteById(commentId);
        return mapToDTO(comment);
    }

    public CommentDTO updateComment(CommentDTO commentDTO, Long id) {
        Comment detachedComment = mapToEntity(commentDTO);
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", id));
        comment.setBody(detachedComment.getBody());
        comment.setName(detachedComment.getName());
        comment.setEmail(detachedComment.getEmail());
        return mapToDTO(commentRepository.save(comment));
    }
}
