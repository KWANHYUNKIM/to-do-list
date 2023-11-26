package com.example.demo.controller;

import com.example.demo.controller.form.BoardForm;
import com.example.demo.domain.Board;
import com.example.demo.domain.Member;
import com.example.demo.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

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
    public String create(HttpSession session, @Valid BoardForm form, MultipartFile file, BindingResult result) throws IOException {
        if(result.hasErrors()) {
            return "boards/createBoardForm";
        }
        // 파일 저장
        String projectPath = System.getProperty("user.dir") + "//src//main//resources//static//images";

        /*식별자 . 랜덤으로 이름 만들어줌*/
        UUID uuid = UUID.randomUUID();

        /*랜덤식별자_원래파일이름 = 저장될 파일이름 지정*/
        String fileName = uuid + "_" + file.getOriginalFilename();

        /*빈 껍데기 생성*/
        /*File을 생성할건데, 이름은 "name" 으로할거고, projectPath 라는 경로에 담긴다는 뜻*/
        File saveFile = new File(projectPath, fileName);

        file.transferTo(saveFile);

        System.out.println("FileName =" + fileName);
        Member member = (Member) session.getAttribute("loginMember");
        System.out.println("Member from session: " + member);
        Board board = new Board();
        board.setTitle(form.getTitle());
        board.setContent(form.getContents());
        board.setDeleteYn('y');
        board.setMember(member);
        board.setFilename(fileName);
        board.setFilepath("/images/" + fileName);

        boardService.join(board);

        return "redirect:";
    }

    @GetMapping("/members/all")
    public String list(Model model) {
        List<Board> boards = boardService.findBoard();
        model.addAttribute("boards",boards);
        return "boards/boardList";
    }

    @GetMapping("/boards/search")
    public String find(@RequestParam("query") String query, Model model){

        List<Board> boards = boardService.findByQuery(query);
        model.addAttribute("boards",boards);
        return "boards/boardList";
    }

    /**
     * 게시판 삭제
     **/
    @GetMapping("/boards/delete/{boardId}")
    public String deleteBoard(@PathVariable("boardId") Long boardId){
        boardService.deleteBoard(boardId);
        return "fragments/boardTable";
    }

    /**
     * 세부 게시판
     **/
    @GetMapping("/boards/{boardId}")
    public String viewBoardDetails(@PathVariable("boardId") Long boardId, Model model) {
        Board board = boardService.findBoardByDetail(boardId);

        if (board == null) {
            // 게시판이 존재하지 않을 경우 예외 처리 또는 적절한 처리를 수행
            return "error";
        }

        // 조회수 증가 로직 추가
        boardService.incrementViewCount(boardId);

        model.addAttribute("board", board);
        return "boards/details";
    }
    /**
     * 정렬
     **/
    @GetMapping("/boards")
    public String getBoards(Model model, @RequestParam(name = "sort", defaultValue = "createdDate") String sort) {
        List<Board> boards = boardService.getAllBoardsSortedBy(sort);
        model.addAttribute("boards", boards);
        model.addAttribute("currentSort", sort);
        return "fragments/boardTable";
    }

    /**
     *  수정 폼 ( 게시글 수정 )
     */
    @GetMapping ("/boards/edit/{boardId}")
    public String showEditForm(@PathVariable("boardId") Long boardId, Model model) {
        Board board = boardService.findBoardByDetail(boardId);
        model.addAttribute("board", board);
        return "boards/editForm"; // 수정 폼의 Thymeleaf 템플릿 이름
    }

    /**
     * 수정 요청 처리 ( 게시글 수정 )
     */
    @PostMapping("/boards/edit/{boardId}")
    public String handleEditForm(@PathVariable("boardId") Long boardId, @ModelAttribute Board updatedBoard) {
        boardService.updateBoard(boardId, updatedBoard);
        return "redirect:/boards/boardList/";
    }
}

