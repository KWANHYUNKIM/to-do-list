package com.example.demo.service;

import com.example.demo.domain.Board;
import com.example.demo.domain.Member;
import com.example.demo.repository.BoardRepository;
import com.example.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
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

    public Board findBoardByDetail(Long id) {return boardRepository.findOne(id);}

    public List<Board> findByAll (String title) {return boardRepository.findByAll(title);}

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
        //Sort.Direction direction = Sort.Direction.DESC; // 기본적으로 내림차순 정렬
        //if ("title".equals(sort)) {
        //    return boardRepository.findAll(Sort.by(direction,"title"));
        //} else if ("createdDate".equals(sort)) {
        //    return boardRepository.findAll(Sort.by(direction, "createdDate"));
        //}
        // 다른 정렬 기준이 필요한 경우에도 추가 가능

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
