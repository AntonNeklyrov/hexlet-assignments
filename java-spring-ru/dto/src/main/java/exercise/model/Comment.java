package exercise.model;

import jakarta.persistence.*;

import static jakarta.persistence.GenerationType.IDENTITY;

import lombok.Getter;
import lombok.Setter;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@Table(name = "comments")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Comment {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "postId")
    private Post post;

    private String body;
}
