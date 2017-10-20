package com.sk.wiki.services;

import com.sk.wiki.models.WikiPhilosophyRouter;
import com.sk.wiki.services.common.IGenericRawService;

public interface IWikiPhilosophyRouterService extends IGenericRawService<WikiPhilosophyRouter, Long> {
	WikiPhilosophyRouter saveOrUpdate(final WikiPhilosophyRouter entity);
}
