package com.hyun.oauthboard.domain.member.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Slf4j
@RequiredArgsConstructor
@Controller
public class MemberController {

    @GetMapping("/login")
    public String logInPage() {

        return "login";
    }

    @GetMapping("/githublogin")
    public String githubLogin() {

        return "githublogin";
    }

    @GetMapping("/test")
    public String test() {

        return "test";
    }
}
