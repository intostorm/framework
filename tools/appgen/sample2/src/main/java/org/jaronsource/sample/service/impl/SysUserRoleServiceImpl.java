package org.jaronsource.sample.service.impl;

import org.jaronsource.sample.dao.SysUserRoleDao;
import org.jaronsource.sample.domain.SysUserRole;
import org.jaronsource.sample.service.SysUserRoleService;
import com.ccesun.framework.core.service.SearchFormSupportService;
import org.springframework.beans.factory.annotation.Autowired;
import com.ccesun.framework.core.dao.support.IDao;
import org.springframework.stereotype.Service;

@Service
public class SysUserRoleServiceImpl extends SearchFormSupportService<SysUserRole, Integer> implements SysUserRoleService {

	@Autowired
	private SysUserRoleDao sysUserRoleDao;
	
	@Override
	public IDao<SysUserRole, Integer> getDao() {
		return sysUserRoleDao;
	}

}