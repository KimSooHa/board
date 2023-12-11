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
@ToString(of = {"id"})
@Table(name = "POST_TAG")
public class PostTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOARD_TAG_ID")
    private Long id;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "POST_NO", referencedColumnName = "POST_NO"),
            @JoinColumn(name = "BOARD_CD", referencedColumnName = "BOARD_CD")
    })
    private Post post;

    @ManyToOne
    @JoinColumn(name = "TAG_NO")
    private Tag tag;


    public PostTag(Post post, Tag tag) {
        this.post = post;
        this.tag = tag;
    }
}
