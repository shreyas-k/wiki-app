package com.sk.wiki.repository.common;

import java.io.Serializable;

import com.sk.wiki.models.common.contracts.ICrudOperations;

public interface ICrudRepository<T extends Serializable, ID extends Serializable> extends ICrudOperations<T, ID> {
	Class<T> getEntityClass();
}
