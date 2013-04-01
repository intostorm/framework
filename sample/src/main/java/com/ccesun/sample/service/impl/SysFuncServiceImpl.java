package com.ccesun.sample.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccesun.framework.core.dao.support.IDao;
import com.ccesun.framework.core.dao.support.Page;
import com.ccesun.framework.core.dao.support.PageRequest;
import com.ccesun.framework.core.dao.support.QCriteria;
import com.ccesun.framework.core.dao.support.SearchForm;
import com.ccesun.framework.core.dao.support.Sort;
import com.ccesun.framework.core.service.SearchFormSupportService;
import com.ccesun.sample.dao.SysFuncDao;
import com.ccesun.sample.domain.SysFunc;
import com.ccesun.sample.service.SysFuncService;

@Service
public class SysFuncServiceImpl extends SearchFormSupportService<SysFunc, Integer> implements SysFuncService {

	@Autowired
	private SysFuncDao sysFuncDao;
	
	@Override
	public IDao<SysFunc, Integer> getDao() {
		return sysFuncDao;
	}

	@Override
	public Page<SysFunc> findPage(SearchForm searchForm) {
		
		QCriteria criteria = super.parseForm(searchForm);
		PageRequest pageRequest = searchForm.getPageRequest();
		Sort sort = new Sort().asc("funcGroupCode").asc("parent.funcId").asc("funcOrder");
		pageRequest.setSort(sort);
		
		return getDao().findPage(pageRequest, criteria);
	}

	@Override
	public List<SysFunc> findAll() {
		Sort sort = new Sort().asc("funcGroupCode").asc("parent.funcId").asc("funcOrder");
		return getDao().find(sort, new QCriteria());
	}
	
	

}