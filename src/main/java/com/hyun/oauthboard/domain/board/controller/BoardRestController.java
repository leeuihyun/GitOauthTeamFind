package com.hyun.oauthboard.domain.board.controller;

import com.hyun.oauthboard.domain.board.dto.BoardCreateRequestDto;
import com.hyun.oauthboard.domain.board.dto.BoardIdReponse;
import com.hyun.oauthboard.domain.board.dto.BoardResponse;
import com.hyun.oauthboard.domain.board.service.BoardService;
import com.hyun.oauthboard.jwt.JwtPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardRestController {

    private final BoardService boardService;

    @GetMapping("/{boardId}")
    public ResponseEntity<BoardResponse> searchBoard(
        @AuthenticationPrincipal JwtPayload jwtPayload, @PathVariable Long boardId) {

        BoardResponse board = boardService.readBoard(jwtPayload, boardId);
        return ResponseEntity.ok().body(board);
    }

    @PostMapping
    public ResponseEntity<BoardIdReponse> writeBoard(@AuthenticationPrincipal JwtPayload jwtPayload,
        @RequestBody BoardCreateRequestDto boardCreateRequestDto) {

        BoardIdReponse response = boardService.createBoard(jwtPayload, boardCreateRequestDto);

        return ResponseEntity.ok().body(response);
    }

    @PutMapping
    public ResponseEntity<BoardIdReponse> updateBoard(
        @AuthenticationPrincipal JwtPayload jwtPayload,
        @RequestBody BoardCreateRequestDto boardCreateRequestDto) {

        BoardIdReponse response = boardService.updateBoard(jwtPayload, boardCreateRequestDto);

        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<Void> deleteBoard(@AuthenticationPrincipal JwtPayload jwtPayload,
        @PathVariable Long boardId) {

        boardService.deleteBoard(jwtPayload, boardId);

        return ResponseEntity.ok().build();
    }
}
