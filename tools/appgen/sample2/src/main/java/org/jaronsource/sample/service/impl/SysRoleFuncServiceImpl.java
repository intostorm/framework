package org.jaronsource.sample.service.impl;

import org.jaronsource.sample.dao.SysRoleFuncDao;
import org.jaronsource.sample.domain.SysRoleFunc;
import org.jaronsource.sample.service.SysRoleFuncService;
import com.ccesun.framework.core.service.SearchFormSupportService;
import org.springframework.beans.factory.annotation.Autowired;
import com.ccesun.framework.core.dao.support.IDao;
import org.springframework.stereotype.Service;

@Service
public class SysRoleFuncServiceImpl extends SearchFormSupportService<SysRoleFunc, Integer> implements SysRoleFuncService {

	@Autowired
	private SysRoleFuncDao sysRoleFuncDao;
	
	@Override
	public IDao<SysRoleFunc, Integer> getDao() {
		return sysRoleFuncDao;
	}

}