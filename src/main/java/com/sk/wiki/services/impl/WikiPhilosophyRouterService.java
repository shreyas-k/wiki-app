package com.sk.wiki.services.impl;

import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sk.wiki.models.WikiPhilosophyRouter;
import com.sk.wiki.models.common.lang.InternalError;
import com.sk.wiki.repository.IWikiPhilosophyRouterRepository;
import com.sk.wiki.repository.common.ICrudRepository;
import com.sk.wiki.services.IArticleService;
import com.sk.wiki.services.IWikiPhilosophyRouterService;
import com.sk.wiki.services.common.impl.AbstractRawService;

@Service
public class WikiPhilosophyRouterService extends AbstractRawService<WikiPhilosophyRouter, Long>
		implements IWikiPhilosophyRouterService {

	@Autowired
	IWikiPhilosophyRouterRepository repository;

	@Autowired
	IArticleService articleService;

	@Override
	protected ICrudRepository<WikiPhilosophyRouter, Long> getRepository() {
		return repository;
	}

	@Override
	public WikiPhilosophyRouter saveOrUpdate(final WikiPhilosophyRouter entity) {
		try {
			entity.computePath();
		} catch (final IOException e) {
			e.printStackTrace();
			throw new InternalError("Internal Error", e);
		}

		// save articles
		entity.getVisitedArticles().forEach(article -> {
			article = articleService.saveOrUpdate(article);
			if (article.getUrl().equals(entity.getSourceArticle().getUrl())) {
				entity.setSourceArticle(article);
			} else if (article.getUrl().equals(entity.getDestinationArticle().getUrl())) {
				entity.setDestinationArticle(article);
			}
		});
		entity.setUpdatedAt(new Date());

		final WikiPhilosophyRouter _entity = repository.findOne(entity.getSourceArticle(),
				entity.getDestinationArticle());
		if (_entity != null) {
			entity.setId(_entity.getId());
		}

		return _entity == null ? super.save(entity) : update(entity);
	}

}
