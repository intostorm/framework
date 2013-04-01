package com.ccesun.sample.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ccesun.framework.core.dao.support.IDao;
import com.ccesun.framework.core.service.SearchFormSupportService;
import com.ccesun.sample.dao.SysFuncDao;
import com.ccesun.sample.dao.SysRoleDao;
import com.ccesun.sample.domain.SysFunc;
import com.ccesun.sample.domain.SysRole;
import com.ccesun.sample.service.SysRoleService;

@Service
public class SysRoleServiceImpl extends SearchFormSupportService<SysRole, Integer> implements SysRoleService {

	@Autowired
	private SysRoleDao sysRoleDao;
	
	@Autowired
	private SysFuncDao sysFuncDao;
	
	@Override
	public IDao<SysRole, Integer> getDao() {
		return sysRoleDao;
	}

	@Override
	@Transactional
	public void assignFunc(Integer roleId, Integer[] funcIds) {
		
		SysRole sysRole = getDao().findReferenceByPk(roleId);
		
		List<SysFunc> functions = new ArrayList<SysFunc>();
		for(Integer funcId : funcIds) {
			SysFunc sysFunc = sysFuncDao.findReferenceByPk(funcId);
			functions.add(sysFunc);
		}
		
		sysRole.setFunctions(functions);
		
		getDao().save(sysRole);
	}

}