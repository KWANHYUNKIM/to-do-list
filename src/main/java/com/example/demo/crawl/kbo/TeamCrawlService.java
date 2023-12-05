package com.example.demo.crawl.kbo;

import com.example.demo.crawl.domain.TeamCrawl;
import com.example.demo.crawl.repository.TeamCrawlRepository;
import com.example.demo.domain.kbo.Team;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TeamCrawlService {
    private static String homepage = "https://www.koreabaseball.com/Record/TeamRank/TeamRank.aspx";
    @Autowired
    private final TeamCrawlRepository teamCrawlRepository;
    @PostConstruct
    public List<TeamCrawl> getNewsDatas() throws IOException {
        List<TeamCrawl> newsList = new ArrayList<>();
        Document document = Jsoup.connect(homepage).get();

        Elements theads = document.select("table.tData");

        if (theads.size() > 0) {
            /**
             *  If Crawl error
             *  checking
             */
            /*
            for(int i = 0 ; i <= 108; i +=12){
                Elements tdElementsInTable = theads.select("td");
                System.out.println("순위" + tdElementsInTable.get(i));
                System.out.println("팀이름" + tdElementsInTable.get(i+ 1));
                System.out.println("경기" + tdElementsInTable.get(i + 2));
                System.out.println("승" + tdElementsInTable.get(i + 3));
                System.out.println("패" + tdElementsInTable.get(i + 4));
                System.out.println("무" + tdElementsInTable.get(i + 5));
                System.out.println("승률" + tdElementsInTable.get(i + 6));
                System.out.println("게임차" + tdElementsInTable.get(i + 7));
                System.out.println("최근10경기" + tdElementsInTable.get(i + 8));
                System.out.println("연속" + tdElementsInTable.get(i + 9));
                System.out.println("홀" + tdElementsInTable.get(i + 10));
                System.out.println("어웨이" + tdElementsInTable.get(i + 11));
            }
            */

            for (int i = 0; i <= 108; i += 12) {
                Elements tdElementsInTable = theads.select("td");

                TeamCrawl teamCrawl = TeamCrawl.builder()
                        .ranking(Integer.parseInt(tdElementsInTable.get(i).text())) // 팀 순위
                        .teamName(tdElementsInTable.get(i + 1).text()) // 팀 이름
                        .matches(Integer.parseInt(tdElementsInTable.get(i + 2).text())) // 경기
                        .win(Integer.parseInt(tdElementsInTable.get(i + 3).text())) // 승
                        .loss(Integer.parseInt(tdElementsInTable.get(i + 4).text())) // 패
                        .draw(Integer.parseInt(tdElementsInTable.get(i + 5).text())) // 무
                        .winningPercentage(Double.parseDouble(tdElementsInTable.get(i + 6).text())) // 승률
                        .gameDifference(tdElementsInTable.get(i + 7).text()) // 게임차
                        .recent10Games(tdElementsInTable.get(i + 8).text()) // 최근10경기
                        .streak(tdElementsInTable.get(i + 9).text()) // 연속
                        .home(tdElementsInTable.get(i + 10).text()) // 홈
                        .away(tdElementsInTable.get(i + 11).text()) // 방문
                        .build();
                newsList.add(teamCrawl);
                save(teamCrawl);
                //System.out.println(teamCrawl);
            }
        }

        return newsList;
    }

    public void save(TeamCrawl teamCrawl){
        teamCrawlRepository.save(teamCrawl);
    }
}