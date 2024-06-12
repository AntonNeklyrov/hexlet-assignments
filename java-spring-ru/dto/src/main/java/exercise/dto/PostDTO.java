package exercise.dto;

import java.util.Collections;
import java.util.List;

import exercise.model.Post;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PostDTO {
    private long id;
    private String title;
    private String body;
    private List<CommentDTO> comments;

    public static PostDTO toDto(Post model) {
        PostDTO dto = new PostDTO();
        dto.setId(model.getId());
        dto.setTitle(model.getTitle());
        dto.setBody(model.getBody());
        dto.setComments(model.getComments().stream().map(CommentDTO::toDto).toList());
        return dto;
    }

    public static List<PostDTO> toDtos(List<Post> models) {
        if(models == null) {
            return Collections.emptyList();
        }

        return models.stream().map(PostDTO::toDto).toList();
    }
}
