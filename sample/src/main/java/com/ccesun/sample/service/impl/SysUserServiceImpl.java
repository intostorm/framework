package com.ccesun.sample.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccesun.framework.core.dao.support.IDao;
import com.ccesun.framework.core.service.SearchFormSupportService;
import com.ccesun.sample.dao.SysUserDao;
import com.ccesun.sample.domain.SysUser;
import com.ccesun.sample.service.SysUserService;

@Service
public class SysUserServiceImpl extends SearchFormSupportService<SysUser, Integer> implements SysUserService {

	@Autowired
	private SysUserDao sysUserDao;
	
	@Override
	public IDao<SysUser, Integer> getDao() {
		return sysUserDao;
	}

	

}