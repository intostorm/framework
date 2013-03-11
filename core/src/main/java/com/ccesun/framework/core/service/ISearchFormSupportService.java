package com.ccesun.framework.core.service;

import java.io.Serializable;
import java.util.List;

import com.ccesun.framework.core.dao.support.IEntity;
import com.ccesun.framework.core.dao.support.Page;
import com.ccesun.framework.core.dao.support.SearchForm;

public interface ISearchFormSupportService<T extends IEntity<I>, I extends Serializable> extends IService<T, I> {

	/**
	 * 使用 SearchForm，查出符合条件的分页对象
	 * SearchForm 一般由Controller层组装
	 * 
	 * @param searchForm SearchForm对象
	 * @return 符合条件的分页对象
	 */
	Page<T> findPage(SearchForm searchForm);

	/**
	 * 使用 SearchForm，查出符合条件的对象集合
	 * SearchForm 一般由Controller层组装
	 * 
	 * @param searchForm SearchForm对象
	 * @return 符合条件的对象集合
	 */
	List<T> find(SearchForm searchForm);
}
