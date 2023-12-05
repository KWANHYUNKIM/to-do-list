package com.example.demo.repository.kbo;


import com.example.demo.domain.Board;
import com.example.demo.domain.kbo.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TeamRepository {

    private final EntityManager em;


    // 게시판 전체 검색
    public List<Team> findAll() {
        return em.createQuery("select m from Team m", Team.class)
                .getResultList();
    }

    // 저장
    public void save(Team team) {
        em.persist(team);
    }

}
