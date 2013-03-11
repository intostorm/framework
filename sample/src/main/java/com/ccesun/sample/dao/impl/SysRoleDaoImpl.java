package com.ccesun.sample.dao.impl;

import com.ccesun.sample.domain.SysRole;
import com.ccesun.sample.dao.SysRoleDao;
import com.ccesun.framework.core.dao.support.GenericDao;
import org.springframework.stereotype.Repository;

@Repository
public class SysRoleDaoImpl extends GenericDao<SysRole, Integer> implements SysRoleDao {

}