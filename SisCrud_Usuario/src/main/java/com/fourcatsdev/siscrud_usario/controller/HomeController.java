package com.fourcatsdev.siscrud_usario.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	
	@RequestMapping("/") 
	public String index(Model model) {
		model.addAttribute("msnBemVindo", "Hospital Imperial de Caridade");
		return "publica-index";
	}
	
	@RequestMapping("/login") 
	public String login() {
		return "login";
	}

}
