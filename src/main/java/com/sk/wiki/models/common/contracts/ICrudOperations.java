package com.sk.wiki.models.common.contracts;

import java.io.Serializable;
import java.util.List;

import javax.persistence.metamodel.SingularAttribute;

import com.sk.wiki.models.OrderMode;

public interface ICrudOperations<T extends Serializable, ID extends Serializable> {
	T save(final T entity);

	T findOne(final ID id);

	<Y extends Comparable<? super Y>> T findByProperty(SingularAttribute<T, Y> attribute, Y value);

	List<T> findAll();

	<Y extends Comparable<? super Y>> List<T> findAll(SingularAttribute<T, Y> orderAttribute, OrderMode orderMode);

	Boolean exists(final ID id);

	T update(final T entity);

	void delete(final T entity);

	void deleteById(final ID id);
}
