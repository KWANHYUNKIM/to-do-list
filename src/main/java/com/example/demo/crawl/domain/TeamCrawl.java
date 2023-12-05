package com.example.demo.crawl.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class TeamCrawl {
    @Id
    @GeneratedValue
    @Column(name ="teamcrawl_id")
    private Long id;

    private int ranking;
    private String teamName;
    private int matches;
    private int win;
    private int loss;
    private int draw;
    private double winningPercentage;
    private String gameDifference;
    private String recent10Games;
    private String streak;
    private String home;
    private String away;
}
