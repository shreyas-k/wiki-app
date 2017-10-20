package com.sk.wiki.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sk.wiki.models.Article;

@Controller
public class IndexController {
	@RequestMapping("/")
	public String index(final Model model) {
		model.addAttribute("article", new Article());
		return "index";
	}

}
