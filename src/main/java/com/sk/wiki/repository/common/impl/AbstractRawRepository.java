package com.sk.wiki.repository.common.impl;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

import com.sk.wiki.models.OrderMode;
import com.sk.wiki.repository.common.ICrudRepository;

@SuppressWarnings("unchecked")
public abstract class AbstractRawRepository<T extends Serializable, ID extends Serializable>
		implements ICrudRepository<T, ID> {
	@PersistenceContext
	protected EntityManager em;
	private Class<T> entityClass;

	protected AbstractRawRepository() {
		final Class<T> clazzToSet = getPersistentClass();
		if (clazzToSet == null) {
			throw new IllegalArgumentException("Entity class must not be null!");
		}
		entityClass = clazzToSet;
	}

	protected abstract Class<T> getPersistentClass();

	@Override
	public Class<T> getEntityClass() {
		return entityClass;
	}

	@Override
	public T save(final T entity) {
		em.persist(entity);
		return entity;
	}

	@Override
	public T findOne(final ID id) {
		return em.find(entityClass, id);
	}

	@Override
	public <Y extends Comparable<? super Y>> T findByProperty(final SingularAttribute<T, Y> attribute, final Y value) {
		final CriteriaBuilder builder = em.getCriteriaBuilder();
		final CriteriaQuery<T> query = builder.createQuery(getPersistentClass());

		final Root<T> root = query.from(getPersistentClass());
		query.select(root);

		query.where(builder.equal(root.get(attribute), value));

		final List<T> resultList = em.createQuery(query).setMaxResults(1).getResultList();

		return resultList.size() > 0 ? resultList.get(0) : null;
	}

	@Override
	public List<T> findAll() {
		return findAll(null, null);
	}

	@Override
	public <Y extends Comparable<? super Y>> List<T> findAll(final SingularAttribute<T, Y> orderAttribute,
			final OrderMode orderMode) {
		final CriteriaBuilder builder = em.getCriteriaBuilder();
		final CriteriaQuery<T> query = builder.createQuery(getPersistentClass());

		final Root<T> root = query.from(getPersistentClass());
		query.select(root);

		if (orderAttribute != null) {
			final OrderMode _orderMode = orderMode == null ? OrderMode.ASC : orderMode;

			if (_orderMode.isAscending()) {
				query.orderBy(builder.asc(root.get(orderAttribute)));
			} else {
				query.orderBy(builder.desc(root.get(orderAttribute)));
			}
		}
		return em.createQuery(query).getResultList();
	}

	@Override
	public Boolean exists(final ID id) {
		return em.createQuery("select 1 from " + entityClass.getSimpleName() + " e where e.id = :id")
				.setParameter("id", id)
				.setMaxResults(1)
				.getResultList()
				.size() > 0;
	}

	@Override
	public T update(final T entity) {
		em.merge(entity);
		return entity;
	}

	@Override
	public void delete(final T entity) {
		em.remove(entity);
	}

	@Override
	public void deleteById(final ID id) {
		delete(findOne(id));
	}
}
