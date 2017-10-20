package com.sk.wiki.services.common.impl;

import java.io.Serializable;
import java.util.List;

import javax.persistence.metamodel.SingularAttribute;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.sk.wiki.models.OrderMode;
import com.sk.wiki.models.common.lang.NotFoundException;
import com.sk.wiki.models.common.utils.ValidatorUtil;
import com.sk.wiki.repository.common.ICrudRepository;
import com.sk.wiki.services.common.IGenericRawService;

@Transactional(value = "transactionManager") // is inherited {@Inherited}
public abstract class AbstractRawService<T extends Serializable, ID extends Serializable>
		implements IGenericRawService<T, ID> {
	@Autowired
	Validator validator;

	protected abstract ICrudRepository<T, ID> getRepository();

	@Override
	public T save(final T entity) {
		ValidatorUtil.validateEntityFields(validator, entity);
		return getRepository().save(entity);
	}

	@Override
	public T findOne(final ID id) {
		final T entity = getRepository().findOne(id);
		if (entity == null) {
			throw new NotFoundException(String.format("%s with id %s not found",
					getRepository().getEntityClass().getSimpleName(), String.valueOf(id)));
		}
		return entity;
	}

	@Override
	public <Y extends Comparable<? super Y>> T findByProperty(final SingularAttribute<T, Y> attribute, final Y value) {
		return getRepository().findByProperty(attribute, value);
	}

	@Override
	public List<T> findAll() {
		return getRepository().findAll();
	}

	@Override
	public <Y extends Comparable<? super Y>> List<T> findAll(final SingularAttribute<T, Y> orderAttribute,
			final OrderMode orderMode) {
		return getRepository().findAll(orderAttribute, orderMode);
	}

	@Override
	public Boolean exists(final ID id) {
		return getRepository().exists(id);
	}

	@Override
	public T update(final T entity) {
		return getRepository().update(entity);
	}

	@Override
	public void delete(final T entity) {
		getRepository().delete(entity);
	}

	@Override
	public void deleteById(final ID id) {
		getRepository().deleteById(id);
	}
}