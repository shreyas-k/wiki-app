package com.sk.wiki.services.common;

import java.io.Serializable;

import com.sk.wiki.models.common.contracts.ICrudOperations;


public interface IGenericRawService<T extends Serializable, ID extends Serializable> extends ICrudOperations<T, ID> {
}

