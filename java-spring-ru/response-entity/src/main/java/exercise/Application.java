package exercise;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import exercise.model.Post;

@SpringBootApplication
@RestController
public class Application {
    // Хранилище добавленных постов
    private List<Post> posts = Data.getPosts();

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // BEGIN
    @GetMapping("/posts")
    public ResponseEntity<List<Post>> getPosts () {
        int postSize = posts.size();

        return ResponseEntity.status(HttpStatus.OK)
                .header("X-Total-Count", String.valueOf(postSize))
                .body(posts);
    }

    @GetMapping("/posts/{id}")
    ResponseEntity<Post> getPost (@PathVariable String id) {
        Optional<Post> optionalPost = posts.stream().filter(post -> post.getId().equals(id)).findFirst();

        return optionalPost.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping("/posts")
    ResponseEntity<Post> createPost(@RequestBody Post post) {
        posts.add(post);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(post);
    }

    @PutMapping("/posts/{id}")
    ResponseEntity<Post> updatePost(@PathVariable String id, @RequestBody Post body) {

        Optional<Post> optionalPost = posts.stream().filter(post -> post.getId().equals(id)).findFirst();

        if(optionalPost.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        Post updatedPost = optionalPost.get();
        updatedPost.setBody(body.getBody());
        updatedPost.setTitle(body.getTitle());

        return ResponseEntity.ok(updatedPost);
    }

    @DeleteMapping("/posts/{id}")
    ResponseEntity<Void> deletePost(@PathVariable String id) {
        posts.removeIf(post -> post.getId().equals(id));
        return ResponseEntity.ok().build();
    }
    // END

}
