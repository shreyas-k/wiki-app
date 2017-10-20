package com.sk.wiki.repository;

import com.sk.wiki.models.Article;
import com.sk.wiki.models.WikiPhilosophyRouter;
import com.sk.wiki.repository.common.ICrudRepository;

public interface IWikiPhilosophyRouterRepository extends ICrudRepository<WikiPhilosophyRouter, Long> {
	WikiPhilosophyRouter findOne(Article src, Article destn);
}
