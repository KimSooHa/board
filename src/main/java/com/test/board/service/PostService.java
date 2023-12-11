package com.test.board.service;

import com.test.board.domain.Board;
import com.test.board.domain.Post;
import com.test.board.domain.PostTag;
import com.test.board.domain.Tag;
import com.test.board.dto.PostRequestDto;
import com.test.board.repository.PostRepository;
import com.test.board.repository.PostTagRepository;
import com.test.board.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final PostTagRepository postTagRepository;
    private final BoardService boardService;

    /**
     * 게시물 생성
     */
    @Transactional
    public Post save(PostRequestDto postRequestDto) {
        Post post = new Post(postRequestDto.getTitle(), postRequestDto.getContent(), postRequestDto.getAuthorId());
        Board board = boardService.findByCode(postRequestDto.getCode());
        if(board == null)
            throw new IllegalArgumentException("해당하는 게시판을 찾을 수 없습니다.");

        Post.createPost(post, board);
        return postRepository.save(post);
    }

    /**
     * 게시물 목록 조회
     */
    public List<Post> getListByBoard(String boardCode) {
        Board board = boardService.findByCode(boardCode);
        if(board != null)
            return postRepository.findByBoard(board);
        return Collections.emptyList();
    }

    /**
     * 게시물 단건 조회
     */
    public Post findOneById(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("해당하는 게시물을 찾을 수 없습니다."));
        return post;
    }


    /**
     * 게시물에 태그 추가
     */
    @Transactional
    public Post addTag(Long postId, String tagName) {
        Post post = postRepository.findById(postId).orElse(null);
        if (post != null) {
            Board board = post.getBoard();
            Tag tag = tagRepository.findByNameAndBoard(tagName, board)
                    .orElseGet(() -> tagRepository.save(new Tag(tagName, board)));

            PostTag postTag = new PostTag(post, tag);
            post.getPostTags().add(postTag);
            postRepository.save(post);
            postTagRepository.save(postTag);

            return post;
        }
        return null;
    }

    /**
     * 게시물 수정
     */
    @Transactional
    public void update(Long postId, PostRequestDto postRequestDto) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("해당하는 게시물을 찾을 수 없습니다."));
        if(postRequestDto.getCode().isBlank())
            throw new IllegalStateException("게시판 코드 작성을 해주세요.");
        else {
            Board board = boardService.findByCode(postRequestDto.getCode());
            if(board == null)
                throw new IllegalArgumentException("해당하는 게시판을 찾을 수 없습니다.");
            post.setBoard(board);
        }
        if(!postRequestDto.getTitle().isBlank())
            post.setTitle(postRequestDto.getTitle());
        if(!postRequestDto.getContent().isBlank())
            post.setContent(postRequestDto.getContent());
        if(!postRequestDto.getAuthorId().isBlank())
            post.setAuthorId(postRequestDto.getAuthorId());
    }

    /**
     * 게시물 삭제
     */
    @Transactional
    public void delete(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("해당 게시물은 이미 존재하지 않습니다."));
        postRepository.delete(post);
    }
}
