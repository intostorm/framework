package com.ccesun.sample.service.impl;

import com.ccesun.sample.dao.SysFuncDao;
import com.ccesun.sample.domain.SysFunc;
import com.ccesun.sample.service.SysFuncService;
import com.ccesun.framework.core.service.SearchFormSupportService;
import org.springframework.beans.factory.annotation.Autowired;
import com.ccesun.framework.core.dao.support.IDao;
import org.springframework.stereotype.Service;

@Service
public class SysFuncServiceImpl extends SearchFormSupportService<SysFunc, Integer> implements SysFuncService {

	@Autowired
	private SysFuncDao sysFuncDao;
	
	@Override
	public IDao<SysFunc, Integer> getDao() {
		return sysFuncDao;
	}

}