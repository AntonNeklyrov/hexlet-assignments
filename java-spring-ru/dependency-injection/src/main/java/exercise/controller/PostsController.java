package exercise.controller;

import exercise.repository.CommentRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;

import exercise.model.Post;
import exercise.repository.PostRepository;
import exercise.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/posts")
public class PostsController {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public PostsController(PostRepository postRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    @GetMapping
    List<Post> getPosts() {
        return postRepository.findAll();
    }

    @GetMapping("/{id}")
    Post getPostById(@PathVariable Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post with id " + id + " not found"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Post createPost(@RequestBody Post post) {
        return postRepository.save(post);
    }

    @PutMapping("/{id}")
    Post updatePost(@PathVariable Long id, @RequestBody Post post) {
        postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post with id " + id + " not found"));

        post.setId(id);
        return postRepository.save(post);
    }

    @DeleteMapping("/{id}")
    void deletePost(@PathVariable Long id) {
        postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post with id " + id + " not found"));

        postRepository.deleteById(id);
        commentRepository.deleteByPostId(id);
    }
}