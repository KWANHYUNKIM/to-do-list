package com.example.demo.service.kbo;

import com.example.demo.domain.Board;
import com.example.demo.domain.kbo.Team;
import com.example.demo.repository.BoardRepository;
import com.example.demo.repository.kbo.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;

    /**
     * 게시글 리스트 조회
     */

    @Transactional
    public Long join(Team team) {

        teamRepository.save(team);
        return team.getId();
    }

    // 초기 화면 조회 :
    public List<Team> findBoard() {
        return teamRepository.findAll();
    }
}
