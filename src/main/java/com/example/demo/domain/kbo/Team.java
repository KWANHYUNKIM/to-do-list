package com.example.demo.domain.kbo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@Table(name = "teams")
public class Team {

    @Id
    @GeneratedValue
    @Column(name ="team_id")
    private Long id;
    // 순위
    private int ranking;
    // 팀 이름
    private String teamName ;
    // 경기수
    private int matches;
    // 승리
    private int win;
    // 패배
    private int defeat;
    // 무
    private int draw;
    // 승률
    private double winningRate;
    // 게임차
    private String gamesBehind;
    // 연속
    private String winningSteak;

}
