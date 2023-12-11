package com.test.board.repository;

import com.test.board.domain.Board;
import com.test.board.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByNameAndBoard(String name, Board board);
}