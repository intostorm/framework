package org.jaronsource.sample.service.impl;

import org.jaronsource.sample.dao.SysRoleDao;
import org.jaronsource.sample.domain.SysRole;
import org.jaronsource.sample.service.SysRoleService;
import com.ccesun.framework.core.service.SearchFormSupportService;
import org.springframework.beans.factory.annotation.Autowired;
import com.ccesun.framework.core.dao.support.IDao;
import org.springframework.stereotype.Service;

@Service
public class SysRoleServiceImpl extends SearchFormSupportService<SysRole, Integer> implements SysRoleService {

	@Autowired
	private SysRoleDao sysRoleDao;
	
	@Override
	public IDao<SysRole, Integer> getDao() {
		return sysRoleDao;
	}

}