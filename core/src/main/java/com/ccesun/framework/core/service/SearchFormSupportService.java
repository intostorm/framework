package com.ccesun.framework.core.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.ccesun.framework.core.dao.support.IEntity;
import com.ccesun.framework.core.dao.support.Page;
import com.ccesun.framework.core.dao.support.PageRequest;
import com.ccesun.framework.core.dao.support.QCriteria;
import com.ccesun.framework.core.dao.support.SearchForm;

@Transactional(readOnly = true)
public abstract class SearchFormSupportService<T extends IEntity<I>, I extends Serializable>
		extends GenericService<T, I> implements ISearchFormSupportService<T, I> {

	@Override
	public Page<T> findPage(SearchForm searchForm) {

		PageRequest pageRequest = searchForm.getPageRequest();

		QCriteria criteria = parseForm(searchForm);

		return getDao().findPage(pageRequest, criteria);
	}

	/**
	 * 解释SearchForm生成查询条件
	 * 
	 * @param searchForm
	 * @return
	 */
	protected QCriteria parseForm(SearchForm searchForm) {
		return QCriteria.parseForm(searchForm);
	}

	@Override
	public List<T> find(SearchForm searchForm) {
		QCriteria criteria = parseForm(searchForm);
		return getDao().find(criteria);
	}

}
