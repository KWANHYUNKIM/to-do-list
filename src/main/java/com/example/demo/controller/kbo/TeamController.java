package com.example.demo.controller.kbo;

import com.example.demo.domain.Board;
import com.example.demo.domain.kbo.Team;
import com.example.demo.service.BoardService;
import com.example.demo.service.kbo.TeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
@Slf4j
@Controller
@RequiredArgsConstructor
public class TeamController {
    private final TeamService teamService;
    @GetMapping("/kbo/team/all")
    public String list(Model model) {
        List<Team> teams = teamService.findBoard();
        model.addAttribute("teams",teams);
        return "kbo/datas/team/teamBoardList";
    }
}
