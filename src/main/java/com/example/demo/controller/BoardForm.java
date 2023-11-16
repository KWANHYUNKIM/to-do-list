package com.example.demo.controller;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class BoardForm {
    @NotEmpty
    private String title;
    @NotEmpty
    private String contents;

    private char deleteYn;
}
