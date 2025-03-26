package com.example.module2.controller;


import com.example.module2.entity.Board;
import com.example.module2.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/")
    public String root() { return "redirect:/board"; }

    @GetMapping
    public String list(@RequestParam(defaultValue = "0") int page, Model model){
        Page<Board> board = boardService.getBoards(page);
        model.addAttribute("boards", board.getContent());
        return "board/list";
    }

    @GetMapping("/{id}")
    public String view(@PathVariable Long id, Model model){
        model.addAttribute("board", boardService.getBoard(id));
        return "board/view";
    }

    @GetMapping("/new")
    public String create(Model model) {
        model.addAttribute("board", new Board());
        return "board/form";
    }

    @PostMapping("/new")
    public String createForm(@PathVariable Board board) {
        boardService.createBoard(board);
        return "redirect:/board";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model){
        model.addAttribute("board", boardService.getBoard(id));
        return "board/form";
    }

    @PostMapping("/{id}/edit")
    public String edit(@PathVariable Long id, @ModelAttribute Board board){
        boardService.updateBoard(id, board);
        return "redirect:/board";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id){
        boardService.deleteBoard(id);
        return "redirect:/board";
    }
}
