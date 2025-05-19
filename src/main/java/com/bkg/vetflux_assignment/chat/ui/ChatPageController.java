package com.bkg.vetflux_assignment.chat.ui;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping(value = "page/chat")
@RequiredArgsConstructor
public class ChatPageController {


	@GetMapping(name = "회원 목록", value = "user", produces = MediaType.TEXT_HTML_VALUE)
	public String chatUser() {
		return "chat-user";
	}

	@GetMapping(name = "채팅방", value = "room", produces = MediaType.TEXT_HTML_VALUE)
	public ModelAndView chatRoom(@RequestParam long userId) {
		
		ModelAndView modelAndView = new ModelAndView("chat-room");
		modelAndView.addObject("userId", userId);
		return modelAndView;
	}
    
}
