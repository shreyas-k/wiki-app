package com.sk.wiki.models.common.contracts;

import java.io.Serializable;

public interface IWithId<ID extends Serializable> extends Serializable {
	ID getId();

	void setId(final ID id);
}
