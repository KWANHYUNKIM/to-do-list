package com.example.demo.controller;

import com.example.demo.controller.form.BoardForm;
import com.example.demo.controller.form.CommentForm;
import com.example.demo.controller.form.LoginForm;
import com.example.demo.domain.Board;
import com.example.demo.domain.Comment;
import com.example.demo.domain.Member;
import com.example.demo.service.BoardService;
import com.example.demo.service.CommentService;
import com.example.demo.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final BoardService boardService;
    @GetMapping("/boards/{boardId}/comment")
    public String showForm(@PathVariable Long boardId, @ModelAttribute("commentForm") CommentForm form, Model model) {
        List<Comment> comments = commentService.findByBoardId(boardId);
        model.addAttribute("comments", comments);
        return "redirect:/boards/" + boardId;
    }

    @PostMapping("/boards/{boardId}/comment")
    public String createForm(@PathVariable Long boardId, HttpSession session, @Valid CommentForm form) throws IOException {
        Comment comment = new Comment();
        Member member = (Member) session.getAttribute("loginMember");
        Board board = boardService.findBoardByDetail(boardId);  // 해당 boardId에 대한 게시물을 가져옴
        comment.setComment(form.getComment());
        comment.setMember(member);
        comment.setBoard(board);  // Comment 엔티티의 board 속성에 Board 객체를 설정

        // 여기서 boardId를 사용하여 해당 게시물을 식별하여 댓글을 저장합니다.
        commentService.join(comment);

        // 댓글이 속한 게시물의 세부 페이지로 리다이렉트합니다.
        return "redirect:/boards/" + boardId;
    }
}