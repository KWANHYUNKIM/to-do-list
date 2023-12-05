package com.example.demo.crawl.repository;

import com.example.demo.crawl.domain.TeamCrawl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;


@Repository
@RequiredArgsConstructor
@Transactional
public class TeamCrawlRepository {
    private final EntityManager em;

    // 저장
    public void save(TeamCrawl teamCrawl){
        em.persist(teamCrawl);
    }
}
