package exercise.controller;

import exercise.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

import exercise.model.Post;
import exercise.repository.PostRepository;
import exercise.exception.ResourceNotFoundException;
import exercise.dto.PostDTO;


@RestController
@RequestMapping("/posts")
public class PostsController {

    private final PostRepository postRepository;

    public PostsController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @GetMapping
    public List<PostDTO> getPosts() {
        List<Post> posts = postRepository.findAll();

        return PostDTO.toDtos(posts);
    }

    @GetMapping("/{id}")
    public PostDTO getPostById(@PathVariable long id) {
        Post optionalPost = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post with id " + id + " not found"));

        return PostDTO.toDto(optionalPost);
    }


}
