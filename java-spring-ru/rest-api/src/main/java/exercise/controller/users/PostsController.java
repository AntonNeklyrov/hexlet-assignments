package exercise.controller.users;

import exercise.model.Post;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

// BEGIN
@RestController
@RequestMapping("/api")
public class PostsController {

    private final List<Post> posts = new ArrayList<>();

    @GetMapping("/users/{id}/posts")
    List<Post> getPosts(@PathVariable int id) {
        return posts.stream().filter(post -> post.getUserId() == id).toList();
    }

    @PostMapping("/users/{id}/posts")
    ResponseEntity<Post> createPost(@PathVariable int id, @RequestBody Post post) {
        post.setUserId(id);
        posts.add(post);
        return ResponseEntity.status(HttpStatus.CREATED).body(post);
    }
}
// END
