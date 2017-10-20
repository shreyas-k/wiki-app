package com.sk.wiki.models;

import org.jsoup.select.Elements;

public class PageInfo {
	private String title;
	private Elements paras;

	public String getTitle() {
		return title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public Elements getParas() {
		return paras;
	}

	public void setParas(final Elements paras) {
		this.paras = paras;
	}
}
