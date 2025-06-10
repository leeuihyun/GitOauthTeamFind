package com.hyun.oauthboard.controller;

import com.hyun.oauthboard.domain.dto.board.BoardCreateRequestDto;
import com.hyun.oauthboard.domain.dto.board.BoardResponse;
import com.hyun.oauthboard.jwt.JwtPayload;
import com.hyun.oauthboard.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Slf4j
@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/home")
    public String mainController() {
        return "home";
    }

    @GetMapping("/write")
    public String writeBoardController() {
        return "write";
    }

    @GetMapping("/board/{boardId}")
    public ResponseEntity<BoardResponse> searchBoard(
        @AuthenticationPrincipal JwtPayload jwtPayload, @PathVariable Long boardId) {

        BoardResponse board = boardService.readBoard(jwtPayload, boardId);

        return ResponseEntity.ok().body(board);
    }

    @PostMapping("/board")
    public ResponseEntity<BoardResponse> writeBoard(@AuthenticationPrincipal JwtPayload jwtPayload,
        @RequestBody BoardCreateRequestDto boardCreateRequestDto) {

        BoardResponse board = boardService.createBoard(jwtPayload, boardCreateRequestDto);

        return ResponseEntity.ok().body(board);
    }

    @PutMapping("/board")
    public ResponseEntity<BoardResponse> updateBoard(@AuthenticationPrincipal JwtPayload jwtPayload,
        @RequestBody BoardCreateRequestDto boardCreateRequestDto) {
        BoardResponse board = boardService.updateBoard(jwtPayload, boardCreateRequestDto);

        return ResponseEntity.ok().body(board);
    }

    @DeleteMapping("/board/{boardId}")
    public ResponseEntity<Void> deleteBoard(@AuthenticationPrincipal JwtPayload jwtPayload,
        @PathVariable Long boardId) {

        boardService.deleteBoard(jwtPayload, boardId);

        return ResponseEntity.ok().build();
    }
}
