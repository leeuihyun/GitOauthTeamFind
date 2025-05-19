package com.hyun.oauthboard.security;

import java.util.List;

public class SecurityConstants {

    public static final List<String> EXCLUDE_URLS = List.of(
        "/home", "/home/**", "/login", "/login/oauth2/**", "/githublogin",
        "/github/oauth"
    );
}
