package com.ccesun.framework.core.dao.support;

import java.io.Serializable;

public interface IEntity<I extends Serializable> extends Serializable {

    boolean isNew();
}
