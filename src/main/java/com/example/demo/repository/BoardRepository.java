package com.example.demo.repository;

import com.example.demo.domain.Board;
import com.example.demo.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BoardRepository {

    private final EntityManager em;

    // 저장
    public void save(Board board) {
        em.persist(board);
    }
    public List<Board> findAll() {
        return em.createQuery("select m from Board m", Board.class)
                .getResultList();
    }

    // 게시판 삭제
    public Optional<Board> findByBoard(String username){
        return findAll().stream()
                .filter(m -> m.getMember().getUsername().equals(username))
                .findFirst();
    }

    // 타이틀 중복

    public Optional<Board> findByTitle(String title){
        return findAll().stream()
                .filter(m ->m.getTitle().equals(title))
                .findFirst();
    }

}
