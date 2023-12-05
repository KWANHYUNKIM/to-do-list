package com.example.demo.controller.form.kbo;

import javax.validation.constraints.NotEmpty;

/**
 * 팀 이름, 승 패 기입 후,  자동 으로 순위, 승률, 게임차, 연속
 *
 */


public class TeamForm {
    @NotEmpty
    private String teamName;
    @NotEmpty
    private int win;
    @NotEmpty
    private int defeat;
}
