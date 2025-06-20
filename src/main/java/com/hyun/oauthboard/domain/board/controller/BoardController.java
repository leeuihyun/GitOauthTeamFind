package com.hyun.oauthboard.domain.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Slf4j
@Controller
@RequiredArgsConstructor
public class BoardController {


    @GetMapping("/home")
    public String mainController() {
        return "home";
    }

    @GetMapping("/write")
    public String writeBoardController() {
        return "write";
    }
}
