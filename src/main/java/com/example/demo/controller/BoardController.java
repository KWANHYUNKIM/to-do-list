package com.example.demo.controller;

import com.example.demo.domain.Board;
import com.example.demo.domain.Member;
import com.example.demo.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/members/board")
    public String boardForm(@ModelAttribute("boardForm") BoardForm form) {
        return "boards/createBoardForm";
    }

    @PostMapping("/members/board")
    public String create(@Valid BoardForm form, BindingResult result) {

        if(result.hasErrors()) {
            return "boards/createBoardForm";
        }

        Board board = new Board();
        board.setTitle(form.getTitle());
        board.setContent(form.getContents());

        boardService.join(board);

        return "redirect:/";
    }

    @GetMapping("/members/all")
    public String list(Model model) {
        List<Board> boards = boardService.findBoard();
        model.addAttribute("boards",boards);
        return "boards/boardList";
    }

}
