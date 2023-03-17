package com.springboot.blog.controller;

import com.springboot.blog.dto.PostDTO;
import com.springboot.blog.dto.PostPage;
import com.springboot.blog.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }


    @GetMapping("/getAllPosts")
    public List<PostDTO> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable(name = "id") long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @GetMapping
    public PostPage getPostsByPage(@RequestParam(defaultValue = "0", required = false) Integer pageNo, @RequestParam(defaultValue = "10", required = false) Integer pageSize, @RequestParam(defaultValue = "id", required = false) String sortBy, @RequestParam(defaultValue = "asc", required = false) String sortDir) {
        return postService.getPostsByPage(pageNo, pageSize, sortBy, sortDir);
    }

    @PostMapping
    public ResponseEntity<PostDTO> createPost(@RequestBody @Valid PostDTO postDto) {
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> updatePost(@RequestBody @Valid PostDTO postDto, @PathVariable(name = "id") long id) {

        PostDTO postResponse = postService.updatePost(postDto, id);

        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")

    public ResponseEntity<String> deletePost(@PathVariable(name = "id") long id) {

        postService.deletePostById(id);

        return new ResponseEntity<>("Post entity deleted successfully.", HttpStatus.OK);
    }
}
