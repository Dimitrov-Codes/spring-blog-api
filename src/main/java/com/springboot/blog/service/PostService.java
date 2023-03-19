package com.springboot.blog.service;

import com.springboot.blog.dto.PostDTO;
import com.springboot.blog.dto.PostPage;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.models.blog.Category;
import com.springboot.blog.models.blog.Post;
import com.springboot.blog.repository.CategoryRepository;
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
    private final CategoryRepository categoryRepository;

    public PostService(PostRepository postRepository, ModelMapper mapper,
                       CategoryRepository categoryRepository) {
        this.postRepository = postRepository;
        this.mapper = mapper;
        this.categoryRepository = categoryRepository;
    }
    // convert Entity into DTO
    private PostDTO mapToDTO(Post post) {
        PostDTO postDTO = mapper.map(post, PostDTO.class);
        postDTO.setCategoryId(post.getCategory().getId());
        return postDTO;
    }

    // convert DTO to entity
    private Post mapToEntity(PostDTO postDto) {
        return mapper.map(postDto, Post.class);
    }

    public PostDTO createPost(PostDTO postDto) {

        // convert DTO to entity
        Category category = categoryRepository.findById(postDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", postDto.getCategoryId()));
        Post post = mapToEntity(postDto);
        post.setCategory(category);
        Post newPost = postRepository.save(post);

        // convert entity to DTO
        return mapToDTO(newPost);
    }

    public List<PostDTO> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public PostDTO getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        return mapToDTO(post);
    }

    public PostDTO updatePost(PostDTO postDto, Long id) {
        // get post by id from the database
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        Category category = categoryRepository.findById(postDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        post.setCategory(category);
        Post updatedPost = postRepository.save(post);
        return mapToDTO(updatedPost);
    }

    public void deletePostById(long id) {
        // get post by id from the database
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        postRepository.delete(post);
    }


    public PostPage getPostsByPage(Integer pageNo, Integer pageSize, String sortBy, String sortDir) {
        Sort sort = Sort.by(sortBy);
        if (sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())) sort = sort.ascending();
        else sort = sort.descending();
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize, sort);

        Page<Post> posts = postRepository.findAll(pageRequest);
        List<PostDTO> listOfPosts = posts.getContent()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());

        return new PostPage(listOfPosts, posts.getNumber(), posts.getSize(),
                posts.getTotalElements(), posts.getTotalPages(), posts.isLast());


    }
}
