package com.hyun.oauthboard.controller;

import com.hyun.oauthboard.domain.dto.board.BoardCreateRequestDto;
import com.hyun.oauthboard.domain.dto.board.BoardResponse;
import com.hyun.oauthboard.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Slf4j
@Controller
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/home")
    public String mainController() {
        return "home";
    }

    @GetMapping("/write")
    public String writeBoardController() {
        return "write";
    }

    @PostMapping("/board")
    public ResponseEntity<BoardResponse> writeBoard(Authentication authentication,
        @RequestBody BoardCreateRequestDto boardCreateRequestDto) {
        BoardResponse board = boardService.createBoard(authentication, boardCreateRequestDto);

        return ResponseEntity.ok().body(board);
    }

    @PutMapping("/board")
    public ResponseEntity<BoardResponse> updateBoard(Authentication authentication,
        @RequestBody BoardCreateRequestDto boardCreateRequestDto) {
        BoardResponse board = boardService.updateBoard(authentication, boardCreateRequestDto);

        return ResponseEntity.ok().body(board);
    }

    @DeleteMapping("/board/{boardId}")
    public ResponseEntity<Void> deleteBoard(Authentication authentication,
        @PathVariable Long boardId) {
        boardService.deleteBoard(authentication, boardId);

        return ResponseEntity.ok().build();
    }
}
