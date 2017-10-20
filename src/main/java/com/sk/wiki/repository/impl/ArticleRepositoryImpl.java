package com.sk.wiki.repository.impl;

import org.springframework.stereotype.Repository;

import com.sk.wiki.models.Article;
import com.sk.wiki.repository.IArticleRepository;
import com.sk.wiki.repository.common.impl.AbstractRawRepository;

@Repository
public class ArticleRepositoryImpl extends AbstractRawRepository<Article, Long> implements IArticleRepository {

	@Override
	protected Class<Article> getPersistentClass() {
		return Article.class;
	}

}
