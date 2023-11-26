package com.example.demo.repository;

import com.example.demo.domain.Board;
import com.example.demo.domain.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

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

    //수정
    //to-do
}
