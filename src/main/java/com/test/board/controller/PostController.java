package com.test.board.controller;

import com.test.board.domain.Post;
import com.test.board.dto.PostRequestDto;
import com.test.board.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
@Slf4j
public class PostController {

    private final PostService postService;


    /**
     * 게시물 등록
     */
    @PostMapping
    public ResponseEntity create(@RequestBody @Valid PostRequestDto postRequestDto, BindingResult result) {
        if (result.hasErrors()) {
            log.info("errors={}", result);
            return ResponseEntity.badRequest().body("Invalid input: " + result.getAllErrors());
        }

        try {
            Post createdPost = postService.save(postRequestDto);
            return ResponseEntity.ok(createdPost);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * 게시물 목록 조회
     */
    @GetMapping("/byBoard/{boardCode}")
    public ResponseEntity<List<Post>> list(@PathVariable String boardCode) {
        List<Post> list = postService.getListByBoard(boardCode);
        return ResponseEntity.ok(list);
    }

    /**
     * 게시물 단건 조회
     */
    @GetMapping("/{postId}")
    public ResponseEntity detail(@PathVariable Long postId) {
        try {
            Post post = postService.findOneById(postId);
            return ResponseEntity.ok(post);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    /**
     * 게시물에 태그 추가
     */
    @PostMapping("/{postId}/tags")
    public ResponseEntity<Post> addTag(@PathVariable Long postId, @RequestBody String tagName) {
        Post updatePost = postService.addTag(postId, tagName);
        return updatePost != null ? ResponseEntity.ok(updatePost) : ResponseEntity.notFound().build();
    }

    /**
     * 게시물 수정
     */
    @PutMapping("/{postId}")
    public ResponseEntity update(@PathVariable Long postId, PostRequestDto postRequestDto) {
        try {
            postService.update(postId, postRequestDto);
            return ResponseEntity.ok().body("게시물을 수정하였습니다!");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * 게시물 삭제
     */
    @DeleteMapping("/{postId}")
    public ResponseEntity delete(@PathVariable Long postId) {
        try {
            postService.delete(postId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
