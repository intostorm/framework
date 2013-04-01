package com.ccesun.sample.service;

import com.ccesun.sample.domain.SysRole;
import com.ccesun.framework.core.service.ISearchFormSupportService;

public interface SysRoleService extends ISearchFormSupportService<SysRole, Integer> {

	void assignFunc(Integer roleId, Integer[] funcIds);

}