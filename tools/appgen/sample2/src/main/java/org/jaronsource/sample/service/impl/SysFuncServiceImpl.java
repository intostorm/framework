package org.jaronsource.sample.service.impl;

import org.jaronsource.sample.dao.SysFuncDao;
import org.jaronsource.sample.domain.SysFunc;
import org.jaronsource.sample.service.SysFuncService;
import com.ccesun.framework.core.service.SearchFormSupportService;
import org.springframework.beans.factory.annotation.Autowired;
import com.ccesun.framework.core.dao.support.IDao;
import org.springframework.stereotype.Service;

@Service
public class SysFuncServiceImpl extends SearchFormSupportService<SysFunc, String> implements SysFuncService {

	@Autowired
	private SysFuncDao sysFuncDao;
	
	@Override
	public IDao<SysFunc, String> getDao() {
		return sysFuncDao;
	}

}