package com.example.module2.controller;


import com.example.module2.entity.Board;
import com.example.module2.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/")
    public String root() { return "redirect:/boards"; }

    @GetMapping
    public String list(@RequestParam(defaultValue = "0") int page, Model model){
        Page<Board> boards = boardService.getBoards(page);
        model.addAttribute("boards", boards.getContent());
        return "boards/list";
    }

    @GetMapping("/{id}")
    public String view(@PathVariable Long id, Model model){
        Board board = boardService.getBoard(id);
        if (board == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다.");
        }
        model.addAttribute("board", board);
        return "boards/view";
    }


    @GetMapping("/new")
    public String create(@ModelAttribute Board board) {
        boardService.createBoard(board);
        return "redirect:/boards";
    }

    @PostMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("board", new Board());
        return "boards/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model){
        model.addAttribute("board", boardService.getBoard(id));
        return "boards/form";
    }

    @PostMapping("/{id}/edit")
    public String edit(@PathVariable Long id, @ModelAttribute Board board){
        boardService.updateBoard(id, board);
        return "redirect:/boards";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id){
        boardService.deleteBoard(id);
        return "redirect:/board";
    }
}
