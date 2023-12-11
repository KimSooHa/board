package com.test.board.repository;

import com.test.board.domain.Post;
import com.test.board.domain.PostTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostTagRepository extends JpaRepository<PostTag, Long> {
    List<PostTag> findByPost(Post post);
}