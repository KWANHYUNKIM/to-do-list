package com.example.demo.controller;

import com.example.demo.domain.Board;
import com.example.demo.domain.Member;
import com.example.demo.service.BoardService;
import com.example.demo.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Parameter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final MemberService memberService;

    @GetMapping("/members/board")
    public String boardForm(@ModelAttribute("boardForm") BoardForm form) {
        return "boards/createBoardForm";
    }

    @PostMapping("/members/board")
    public String create(HttpSession session, @Valid BoardForm form, BindingResult result) {

        if(result.hasErrors()) {
            return "boards/createBoardForm";
        }
        Member member = (Member) session.getAttribute("loginMember");
        System.out.println("Member from session: " + member);
        Board board = new Board();
        board.setTitle(form.getTitle());
        board.setContent(form.getContents());
        board.setDeleteYn('y');
        board.setMember(member);
        boardService.join(board);

        return "redirect:/";
    }

    @GetMapping("/members/all")
    public String list(Model model) {
        List<Board> boards = boardService.findBoard();
        model.addAttribute("boards",boards);
        return "boards/boardList";
    }


    @GetMapping("/boards/search")
    public String find(@RequestParam("query") String query, Model model){

        List<Board> boards = boardService.findByAll(query);
        model.addAttribute("boards",boards);
        return "boards/boardList";
    }

    /*
     * 게시판 삭제
     */
    @GetMapping("/boards/delete/{boardId}")
    public String deleteBoard(@PathVariable("boardId") Long boardId){
        System.out.println("BOARDID = " + boardId);
        boardService.deleteBoard(boardId);

        return "boards/boardList";
    }

    /*
     * 세부 게시판
     */

    @GetMapping("/boards/{boardId}")
    public String viewBoardDetails(@PathVariable("boardId") Long boardId, Model model) {
        Board board = boardService.findBoardByDetail(boardId);

        if (board == null) {
            // 게시판이 존재하지 않을 경우 예외 처리 또는 적절한 처리를 수행
            return "error";
        }

        model.addAttribute("board", board);
        return "boards/details";
    }
}

