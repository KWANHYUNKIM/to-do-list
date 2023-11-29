package com.example.demo.service;

import com.example.demo.domain.Board;
import com.example.demo.repository.BoardRepository;
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

    // 초기 화면 조회 :
    public List<Board> findBoard() {
        return boardRepository.findAll();
    }

    public Board findBoardByDetail(Long id) {return boardRepository.findOne(id);}
    // 검색 했을 경우 :
    public List<Board> findByQuery(String title) {return boardRepository.findByQuery(title);}

    /**
     * 게시글 삭제
     */
    @Transactional
    public void deleteBoard(Long boardId) {

        boardRepository.deleteById(boardId);
    }

    /**
     *  게시글 정렬
     */
    @Transactional
    public List<Board> getAllBoardsSortedBy(String sort) {
        //TO-DO


        return boardRepository.findAll(); // 정렬 기준이 없는 경우 기본적으로 전체 목록 반환
    }

    /**
     * 게시글 수정
     */

    @Transactional
    public void updateBoard(Long boardId, Board updatedBoard) {
        Board existingBoard = findBoardByDetail(boardId);

        if (existingBoard != null) {
            existingBoard.setTitle(updatedBoard.getTitle());
            existingBoard.setContent(updatedBoard.getContent());
            // 필요에 따라 다른 필드도 업데이트
            boardRepository.save(existingBoard);
        }
    }

    @Transactional
    public void incrementViewCount(Long boardId) {
        boardRepository.incrementViewCount(boardId);
    }
}
