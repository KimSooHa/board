package com.test.board.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static javax.persistence.CascadeType.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"code", "name"})
@Table(name = "BOARD_DEF")
public class Board {

    @Id
    @Column(name = "BOARD_CD", length = 20)
    private String code;

    @Column(name = "BOARD_NM")
    private String name;

    @OneToMany(mappedBy = "board", cascade = PERSIST)
    private Set<Post> posts = new HashSet<>();

    @OneToMany(mappedBy = "board", cascade = PERSIST)
    private Set<Tag> tags = new HashSet<>();
}
