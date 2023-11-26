package com.example.demo.repository;

import com.example.demo.domain.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class BoardRepository {

    private final EntityManager em;

    // 저장
    public void save(Board board) {
        em.persist(board);
    }

    // 게시판 전체 검색
    public List<Board> findAll() {
        return em.createQuery("select m from Board m", Board.class)
                .getResultList();
    }

    // Search 를 통해서 Title 검색

    public List<Board> findByQuery(String title) {
        return findAll().stream()
                .filter(m -> m.getTitle().contains(title))
                .collect(Collectors.toList());
    }

    // 게시판 삭제
    public void deleteById(Long boardId){
            Board board = findOne(boardId);
            em.remove(board);
    }

    // 타이틀 중복
    public Optional<Board> findByTitle(String title){
        return findAll().stream()
                .filter(m ->m.getTitle().equals(title))
                .findFirst();
    }
    // 게시판 검색
    public Board findOne(Long id){
        return em.find(Board.class, id);
    }

    // 조히수 증가
    public void incrementViewCount(Long boardId) {
        Board board = findOne(boardId);
        if (board != null) {
            board.setViewCount(board.getViewCount() + 1);
            save(board);
        }
    }
}
