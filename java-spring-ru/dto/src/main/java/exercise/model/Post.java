package exercise.model;

import jakarta.persistence.*;

import static jakarta.persistence.GenerationType.IDENTITY;

import lombok.Getter;
import lombok.Setter;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;


@Entity
@Table(name = "posts")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Post {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;

    private String title;

    private String body;

    @OneToMany(mappedBy = "post", orphanRemoval = true)
    private List<Comment> comments;
}
