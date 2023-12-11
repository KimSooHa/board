package com.test.board.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "title", "content", "authorId", "regDate"})
@Table(name = "POST")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POST_NO")
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "BOARD_CD")
    private Board board;

    @Column(name = "POST_SJ")
    private String title;

    @Column(name = "POST_CN", length = 1000)
    private String content;

    @Column(name = "REGSTR_ID", length = 50)
    private String authorId;

    @Column(name = "REG_DT")
    private LocalDateTime regDate;

    @OneToMany(mappedBy = "post")
    private Set<PostTag> postTags = new HashSet<>();

    public Post(String title, String content, String authorId) {
        this.title = title;
        this.content = content;
        this.authorId = authorId;
        this.regDate = LocalDateTime.now();
    }

    // 연관관계 메서드
    public void setBoard(Board board) {
        this.board = board;
        board.getPosts().add(this);
    }

    // 생성 메서드
    public static Post createPost(Post post, Board board) {
        post.setBoard(board);
        return post;
    }
}
