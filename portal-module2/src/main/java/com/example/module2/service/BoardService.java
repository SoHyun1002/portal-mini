package com.example.module2.service;

import com.example.module2.entity.Board;
import com.example.module2.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {
    private final BoardRepository boardRepository;

    public Page<Board> getBoards(int page) {
        return boardRepository.findAllByOrderByIdDesc(PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "id")));
    }

    public Board getBoard(Long id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
    }

    @Transactional
    public Board createBoard(Board board) { return  boardRepository.save(board); }


    @Transactional
    public Board updateBoard(Long id, Board board) {
        Board oldBoard = getBoard(id);
        oldBoard.setTitle(board.getTitle());
        oldBoard.setContent(board.getContent());
        return boardRepository.save(oldBoard);
    }

    @Transactional
    public void deleteBoard(Long id) { boardRepository.deleteById(id); }


}
