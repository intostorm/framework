package com.ccesun.sample.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ccesun.framework.core.dao.support.IDao;
import com.ccesun.framework.core.service.SearchFormSupportService;
import com.ccesun.sample.dao.SysRoleDao;
import com.ccesun.sample.dao.SysUserDao;
import com.ccesun.sample.domain.SysRole;
import com.ccesun.sample.domain.SysUser;
import com.ccesun.sample.service.SysUserService;

@Service
public class SysUserServiceImpl extends SearchFormSupportService<SysUser, Integer> implements SysUserService {

	@Autowired
	private SysUserDao sysUserDao;
	
	@Autowired
	private SysRoleDao sysRoleDao;
	
	@Override
	public IDao<SysUser, Integer> getDao() {
		return sysUserDao;
	}

	@Override
	@Transactional
	public void assignRole(Integer userId, Integer[] roleIds) {
		SysUser sysUser = getDao().findReferenceByPk(userId);
		
		List<SysRole> roles = new ArrayList<SysRole>();
		for(Integer roleId : roleIds) {
			SysRole sysRole = sysRoleDao.findReferenceByPk(roleId);
			roles.add(sysRole);
		}
		
		sysUser.setRoles(roles);
		
		getDao().save(sysUser);
		
	}

	

}