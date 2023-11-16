package com.example.demo.service;

import com.example.demo.domain.Board;
import com.example.demo.domain.Member;
import com.example.demo.repository.BoardRepository;
import com.example.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    /**
     * 게시글 생성
     */

    @Transactional
    public Long join(Board board) {

        validateDuplicateTitle(board); // 중복 타이틀 검증

        boardRepository.save(board);
        return board.getId();
    }

    private void validateDuplicateTitle(Board board) {
        Optional<Board> findboard = boardRepository.findByTitle(board.getTitle());

        if (!findboard.isEmpty()) {
            throw new IllegalStateException("중복된 게시판 이름 입니다.");
        }
    }

    /**
     * 게시글 리스트 조회
     */
    public List<Board> findBoard() {
        return boardRepository.findAll();
    }

    public List<Board> findByAll (String title) {return boardRepository.findByAll(title);}
    /**
     * 게시글 수정
     */

    /**
     * 게시글 삭제
     */

    @Transactional
    public void deleteBoard(Long boardId) {

        boardRepository.deleteById(boardId);
    }
}
