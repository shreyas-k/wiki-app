package com.sk.wiki.repository.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.sk.wiki.models.Article;
import com.sk.wiki.models.WikiPhilosophyRouter;
import com.sk.wiki.models.WikiPhilosophyRouter_;
import com.sk.wiki.repository.IWikiPhilosophyRouterRepository;
import com.sk.wiki.repository.common.impl.AbstractRawRepository;

@Repository
public class WikiPhilosophyRouterRepositoryImpl extends AbstractRawRepository<WikiPhilosophyRouter, Long>
		implements IWikiPhilosophyRouterRepository {

	@Override
	protected Class<WikiPhilosophyRouter> getPersistentClass() {
		return WikiPhilosophyRouter.class;
	}

	@Override
	public WikiPhilosophyRouter findOne(final Article src, final Article destn) {
		final CriteriaBuilder builder = em.getCriteriaBuilder();
		final CriteriaQuery<WikiPhilosophyRouter> query = builder.createQuery(getPersistentClass());

		final Root<WikiPhilosophyRouter> root = query.from(getPersistentClass());
		query.select(root);

		query.where(builder.equal(root.get(WikiPhilosophyRouter_.sourceArticle), src),
				builder.equal(root.get(WikiPhilosophyRouter_.destinationArticle), destn));

		final List<WikiPhilosophyRouter> resultList = em.createQuery(query).setMaxResults(1).getResultList();

		return resultList.size() > 0 ? resultList.get(0) : null;
	}

}
