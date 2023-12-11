package com.test.board.service;

import com.test.board.domain.Board;
import com.test.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    /**
     * 게시판 단건 조회
     */
    public Board findByCode(String code) {
        return boardRepository.findById(code).orElse(null);
    }
}
