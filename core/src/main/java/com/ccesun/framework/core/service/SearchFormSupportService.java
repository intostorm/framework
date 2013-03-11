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
public abstract class SearchFormSupportService<T extends IEntity<I>, I extends Serializable> extends GenericService<T, I> implements ISearchFormSupportService<T, I> {

	@Override
	public Page<T> findPage(SearchForm searchForm) {
		
		PageRequest pageRequest = searchForm.getPageRequest();
		
		QCriteria criteria = parseForm(searchForm);
		
		return getDao().findPage(pageRequest, criteria);
	}
	/**
	 * 解释SearchForm生成查询条件
	 * @param searchForm
	 * @return
	 */
	protected QCriteria parseForm(SearchForm searchForm) {
		
		QCriteria criteria = new QCriteria();
		Map<String, String> form = searchForm.getForm();
		
		
		for (Iterator<Map.Entry<String, String>> iterator = form.entrySet().iterator(); iterator.hasNext();) {
			Map.Entry<String, String> entry = iterator.next();
			String name = entry.getKey();
			String value = entry.getValue();
			
			if(StringUtils.isBlank(value))
				continue;
			
			if(name.contains(SearchForm.OP_GT_SUFFIX)){
				String fieldName = StringUtils.substringBefore(name, SearchForm.OP_GT_SUFFIX);
				Object realValue = converValue(name, value);
				criteria.addEntry(fieldName, ">", realValue);
			}else if(name.contains(SearchForm.OP_LT_SUFFIX)){
				String fieldName = StringUtils.substringBefore(name, SearchForm.OP_LT_SUFFIX);
				Object realValue = converValue(name, value);
				criteria.addEntry(fieldName, "<", realValue);
			}else if(name.contains(SearchForm.OP_LIKE_SUFFIX)){
				String fieldName = StringUtils.substringBefore(name, SearchForm.OP_LIKE_SUFFIX);
				Object realValue = converValue(name, value);
				criteria.addEntry(fieldName, "like", realValue + "%");
			}else if(name.contains(SearchForm.OP_BOTH_LIKE_SUFFIX)){
				String fieldName = StringUtils.substringBefore(name, SearchForm.OP_BOTH_LIKE_SUFFIX);
				Object realValue = converValue(name, value);
				criteria.addEntry(fieldName, "like", "%" + realValue + "%");
			}else if(name.contains(SearchForm.OP_EQ_SUFFIX)){
				String fieldName = StringUtils.substringBefore(name, SearchForm.OP_EQ_SUFFIX);
				Object realValue = converValue(name, value);
				criteria.addEntry(fieldName, "=", realValue);
			}else if(name.contains(SearchForm.OP_NOT_EQ_SUFFIX)){
				String fieldName = StringUtils.substringBefore(name, SearchForm.OP_NOT_EQ_SUFFIX);
				Object realValue = converValue(name, value);
				criteria.addEntry(fieldName, "<>", realValue);
			}else if (name.contains(SearchForm.OP_EQ_AND_GT_SUFFIX)) {
				String fieldName = StringUtils.substringBefore(name, SearchForm.OP_EQ_AND_GT_SUFFIX);
				Object realValue = converValue(name, value);
				criteria.addEntry(fieldName, ">=", realValue);
			}else if (name.contains(SearchForm.OP_EQ_AND_LT_SUFFIX)) {
				String fieldName = StringUtils.substringBefore(name, SearchForm.OP_EQ_AND_LT_SUFFIX);
				Object realValue = converValue(name, value);
				criteria.addEntry(fieldName, "<=", realValue);
			}else{
				continue;
			}
			}
		
		return criteria;
	}
	
	@Override
	public List<T> find(SearchForm searchForm){
		QCriteria criteria = parseForm(searchForm);
		return getDao().find(criteria);
	}

	/**
	 * 把Value转换成真实的类型
	 * @param fieldName
	 * @param value
	 * @return
	 */
	private Object converValue(String fieldName, String value) {
		
		if (fieldName.endsWith(SearchForm.TYPE_DATE)) {
			try {
				return new SimpleDateFormat(DateUtils.PATTERN_DATE).parse(value);
			} catch (ParseException e) {
			}
		}
		
		else if (fieldName.endsWith(SearchForm.TYPE_INT)) {
			return new Integer(String.valueOf(value));
		}
		
		else if (fieldName.endsWith(SearchForm.TYPE_STRING)) {
			return String.valueOf(value);
		}
		
		return String.valueOf(value);
	}
}
