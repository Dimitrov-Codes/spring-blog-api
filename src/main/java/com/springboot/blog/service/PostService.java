package com.springboot.blog.service;

import com.springboot.blog.dto.PostDTO;
import com.springboot.blog.dto.PostPage;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.models.blog.Post;
import com.springboot.blog.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;

    private final ModelMapper mapper;

    public PostService(PostRepository postRepository, ModelMapper mapper) {
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    public PostDTO createPost(PostDTO postDto) {

        // convert DTO to entity
        Post post = mapToEntity(postDto);
        Post newPost = postRepository.save(post);

        // convert entity to DTO
        return mapToDTO(newPost);
    }

    public List<PostDTO> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public PostDTO getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        return mapToDTO(post);
    }

    public PostDTO updatePost(PostDTO postDto, long id) {
        // get post by id from the database
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        Post updatedPost = postRepository.save(post);
        return mapToDTO(updatedPost);
    }

    public void deletePostById(long id) {
        // get post by id from the database
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        postRepository.delete(post);
    }

    // convert Entity into DTO
    private PostDTO mapToDTO(Post post) {
        return mapper.map(post, PostDTO.class);
    }

    // convert DTO to entity
    private Post mapToEntity(PostDTO postDto) {
        return mapper.map(postDto, Post.class);
    }

    public PostPage getPostsByPage(Integer pageNo, Integer pageSize, String sortBy, String sortDir) {
        Sort sort = Sort.by(sortBy);
        if (sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())) sort = sort.ascending();
        else sort = sort.descending();
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize, sort);

        Page<Post> posts = postRepository.findAll(pageRequest);
        List<PostDTO> listOfPosts = posts.getContent().stream().map(this::mapToDTO).collect(Collectors.toList());

        return new PostPage(listOfPosts, posts.getNumber(), posts.getSize(), posts.getTotalElements(), posts.getTotalPages(), posts.isLast());


    }
}
