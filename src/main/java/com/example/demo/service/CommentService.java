package com.example.demo.service;

import com.example.demo.domain.Board;
import com.example.demo.domain.Comment;
import com.example.demo.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private EntityManager em;

    /**
     * 커맨드 생성
     */

    @Transactional
    public Long join(Comment comment) {
        commentRepository.save(comment);
        return comment.getId();
    }
    public List<Comment> findByBoardId(Long boardId) {
        String jpql = "SELECT c FROM Comment c WHERE c.board.id = :boardId";
        return em.createQuery(jpql, Comment.class)
                .setParameter("boardId", boardId)
                .getResultList();
    }

}
