package com.example.demo.repository;

import com.example.demo.domain.Board;
import com.example.demo.domain.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CommentRepository {
    private final EntityManager em;
    //저장

    public void save(Comment comment) {
        em.persist(comment);
    }

    //삭제

    public void deleteById(Long commentId) {
        Comment comment = findOne(commentId);
        em.remove(comment);
    }

    private Comment findOne(Long commentId) {
        return em.find(Comment.class, commentId);
    }

    // 조회

    public List<Comment> findByComment(Long boardId) {
        return em.createQuery("SELECT c  FROM Comment c WHERE c.board.id = :boardId", Comment.class)
                .setParameter("boardId", boardId)
                .getResultList();
    }

    //수정
    //to-do
}
