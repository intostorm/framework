package org.jaronsource.sample.service.impl;

import org.jaronsource.sample.dao.SysAreaDao;
import org.jaronsource.sample.domain.SysArea;
import org.jaronsource.sample.service.SysAreaService;
import com.ccesun.framework.core.service.SearchFormSupportService;
import org.springframework.beans.factory.annotation.Autowired;
import com.ccesun.framework.core.dao.support.IDao;
import org.springframework.stereotype.Service;

@Service
public class SysAreaServiceImpl extends SearchFormSupportService<SysArea, Integer> implements SysAreaService {

	@Autowired
	private SysAreaDao sysAreaDao;
	
	@Override
	public IDao<SysArea, Integer> getDao() {
		return sysAreaDao;
	}

}