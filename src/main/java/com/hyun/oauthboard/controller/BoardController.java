package com.hyun.oauthboard.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Slf4j
@Controller
public class BoardController {

    @GetMapping("/home")
    public String mainController() {
        return "home";
    }
}
