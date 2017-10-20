package com.sk.wiki.services;

import com.sk.wiki.models.Article;
import com.sk.wiki.services.common.IGenericRawService;

public interface IArticleService extends IGenericRawService<Article, Long> {
	Article saveOrUpdate(Article article);
}
