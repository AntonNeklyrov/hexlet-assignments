package exercise.dto;

import exercise.model.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CommentDTO {
    private long id;
    private String body;

    public static CommentDTO toDto(Comment model) {
        CommentDTO dto = new CommentDTO();
        dto.setId(model.getId());
        dto.setBody(model.getBody());
        return dto;
    }
}
