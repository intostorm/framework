package com.ccesun.sample.service.impl;

import com.ccesun.sample.dao.SysRoleDao;
import com.ccesun.sample.domain.SysRole;
import com.ccesun.sample.service.SysRoleService;
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