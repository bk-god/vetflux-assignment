package com.bkg.vetflux_assignment.chat.ui;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping(value = "page/user")
@RequiredArgsConstructor
public class UserPageController {

	@GetMapping(name = "회원가입", value = "join", produces = MediaType.TEXT_HTML_VALUE)
	public String userJoin() {
		return "user-join";
	}

	@GetMapping(name = "로그인", value = "login", produces = MediaType.TEXT_HTML_VALUE)
	public String userLogin() {
		return "user-login";
	}
    
}
