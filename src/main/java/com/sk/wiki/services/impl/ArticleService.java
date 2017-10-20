package com.sk.wiki.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sk.wiki.models.Article;
import com.sk.wiki.models.Article_;
import com.sk.wiki.repository.IArticleRepository;
import com.sk.wiki.repository.common.ICrudRepository;
import com.sk.wiki.services.IArticleService;
import com.sk.wiki.services.common.impl.AbstractRawService;

@Service
public class ArticleService extends AbstractRawService<Article, Long> implements IArticleService {

	@Autowired
	IArticleRepository repository;

	@Override
	protected ICrudRepository<Article, Long> getRepository() {
		return repository;
	}

	@Override
	public Article saveOrUpdate(final Article article) {
		final Article _article = findByProperty(Article_.url, article.getUrl());
		if (_article == null) {
			return save(article);
		} else {
			article.setId(_article.getId());
			return update(article);
		}
	}

}
