package com.ccesun.framework.core.service;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.ccesun.framework.core.dao.support.IEntity;
import com.ccesun.framework.core.dao.support.Page;
import com.ccesun.framework.core.dao.support.PageRequest;
import com.ccesun.framework.core.dao.support.QCriteria;
import com.ccesun.framework.core.dao.support.SearchForm;
import com.ccesun.framework.util.DateUtils;
import com.ccesun.framework.util.StringUtils;

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

	/**
	 * 把Value转换成真实的类型
	 * 
	 * @param fieldName
	 * @param value
	 * @return
	 */
	private Object converValue(String fieldName, String value) {

		return QCriteria.converValue(fieldName, value);
	}
}
