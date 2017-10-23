package com.sk.wiki.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sk.wiki.models.Article;
import com.sk.wiki.models.OrderMode;
import com.sk.wiki.models.WikiPhilosophyRouter;
import com.sk.wiki.models.WikiPhilosophyRouter_;
import com.sk.wiki.models.common.lang.AppException;
import com.sk.wiki.services.IWikiPhilosophyRouterService;

@Controller
@RequestMapping(value = "/wiki/philosophy")
public class WikiController {
	@Autowired
	IWikiPhilosophyRouterService routerService;

	@Value("${philosophy.url}")
	private String philosophyUrl;

	@Value("${wiki_special_random_url}")
	private String wikiSpecialRandomUrl;

	@RequestMapping(value = "/route", method = RequestMethod.POST)
	public String findPathToPhilosophy(final Article article, final Model model) throws IOException {
		final WikiPhilosophyRouter router = new WikiPhilosophyRouter(article.getUrl(), philosophyUrl);

		model.addAttribute("router", routerService.saveOrUpdate(router));
		return "index";
	}

	@RequestMapping(value = "/history", method = RequestMethod.GET)
	public String fetchHistory(final Model model) throws IOException {
		model.addAttribute("routers", routerService.findAll(WikiPhilosophyRouter_.updatedAt, OrderMode.DESC));
		return "history";
	}

	@RequestMapping(value = "/route/{id}", method = RequestMethod.GET)
	public String fetchPhilosophyRoute(@PathVariable final Long id, final Model model) {
		model.addAttribute("router", routerService.findOne(id));
		return "displayRoute";
	}

	@RequestMapping(value = "/random/source", method = RequestMethod.GET)
	public String getRandomSource(final Model model) throws IOException {
		model.addAttribute("article", getRandomArticle());
		return "index";
	}

	@ExceptionHandler(Exception.class)
	public ModelAndView handleError(final HttpServletRequest req, final Exception ex) {
		final ModelAndView mav = new ModelAndView();
		mav.addObject("error", getAppException(ex));
		mav.addObject("article", new Article());
		mav.setViewName("index");
		return mav;
	}

	private AppException getAppException(final Exception ex) {
		final String type = ex.getClass().getSimpleName();
		final String message = ExceptionUtils.getRootCauseMessage(ex);
		return new AppException(type, message);
	}

	private Article getRandomArticle() throws IOException {
		final Connection conn = Jsoup.connect(wikiSpecialRandomUrl);
		final String rUrl = conn.execute().url().toString();
		final Article article = new Article();
		article.setUrl(rUrl);
		return article;
	}
}
