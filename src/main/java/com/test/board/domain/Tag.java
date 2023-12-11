package com.test.board.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "name"})
@Table(name = "TAG")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TAG_NO")
    private Long id;

    @Column(name = "TAG")
    private String name;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "BOARD_CD")
    private Board board;

    @OneToMany(mappedBy = "tag")
    private Set<PostTag> postTags = new HashSet<>();

    public Tag(String name, Board board) {
        this.name = name;
        this.board = board;
    }

    // 연관관계 메서드
    public void setBoard(Board board) {
        this.board = board;
        board.getTags().add(this);
    }

    public void setPostTag(PostTag postTag) {
        this.postTags.add(postTag);
        postTag.setTag(this);
    }

    // 생성 메서드
    public static Tag createTag(Tag tag, PostTag postTag, Board board) {
        tag.setBoard(board);
        tag.setPostTag(postTag);
        return tag;
    }
}
