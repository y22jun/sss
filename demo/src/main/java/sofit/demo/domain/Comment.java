package sofit.demo.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "COMMENT")
@Getter
@NoArgsConstructor
@Entity
public class Comment extends BaseTimeEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne()
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne()
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @Column
    private String content;

    private boolean isRemoved = false;

    public void updateContent(String content) {
        this.content = content;
    }

    public void remove() {
        this.isRemoved = true;
    }

    @Builder
    public Comment(User user, Board board, Comment parent, String content) {
        this.user = user;
        this.board = board;
        this.parent = parent;
        this.content = content;
        this.isRemoved = false;
    }
}
