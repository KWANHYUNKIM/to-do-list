package com.example.demo.crawl.controller;

import com.example.demo.crawl.domain.TeamCrawl;
import com.example.demo.crawl.kbo.TeamCrawlService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class NewsController {

    private final TeamCrawlService teamCrawlService;

    public NewsController(TeamCrawlService teamCrawlService) {
        this.teamCrawlService = teamCrawlService;
    }

    @GetMapping("/news")
    public String news(Model model) throws Exception{
        List<TeamCrawl> teamCrawl = teamCrawlService.getNewsDatas();
        model.addAttribute("teamCrawlList", teamCrawl);

        return "kbo/datas/team/news";
    }
}